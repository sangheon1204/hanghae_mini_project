package com.sparta.miniproject1.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.miniproject1.user.dto.*;

import com.sparta.miniproject1.user.jwt.JwtUtil;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import com.sparta.miniproject1.user.service.KakaoService;
import com.sparta.miniproject1.user.service.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Api(tags = {"Users"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

    @ApiOperation(value = "회원가입", notes = "유저 하나를 추가한다.")
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }
    @ApiOperation(value = "카카오 로그인", notes = "이것은 카카오 로그인 버튼을 누름을 통해서 수행된다.")
    @GetMapping("/kakao/callback")    //카카오로부터 코드 받고, 다시 전달해서
    public ResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String createToken = kakaoService.kakaoLogin(code);
        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ResponseDto("카카오 로그인 완료");
    }
    @ApiOperation(value = "로그인", notes = "입력받은 정보를 기반으로 로그인 작업을 수행한다.")
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
    @ApiOperation(value = "로그인 유저 이름 반환", notes = "로그인 한 유저가 메인페이지를 요청할 때 유저의 이름 반환한다.")
    @GetMapping("/info")
    public String getUserName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getInfo(userDetails.getUser());
    }
    @ApiOperation(value = "비밀번호 변경", notes = "사용자의 비밀번호를 변경한다.")
    @PutMapping("/changepw/{id}")
    public ResponseDto changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequestDto changePasswordRequestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.changePassword(id,changePasswordRequestDto, userDetails.getUser());
    }
    @ApiOperation(value = "계정 삭제", notes = "유저를 삭제한다.(자신 한정)")
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.softDeleteId(id, userDetails.getUser());
    }
}
