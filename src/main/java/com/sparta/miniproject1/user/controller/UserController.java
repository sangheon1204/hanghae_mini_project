package com.sparta.miniproject1.user.controller;

import com.sparta.miniproject1.user.dto.ChangePasswordRequestDto;

import com.sparta.miniproject1.user.dto.LoginRequestDto;
import com.sparta.miniproject1.user.dto.ResponseDto;
import com.sparta.miniproject1.user.dto.SignupRequestDto;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import com.sparta.miniproject1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "유저 하나를 추가한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);

    }
    @ApiOperation(value = "로그인", notes = "입력받은 정보를 기반으로 로그인 작업을 수행한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
    @ApiOperation(value = "로그인 유저 이름 반환", notes = "로그인 한 유저가 메인페이지를 요청할 때 유저의 이름 반환한다.")
    @GetMapping("/info")
    public String getUserName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails.getUsername();
    }
    @ApiOperation(value = "비밀번호 변경", notes = "사용자의 비밀번호를 변경한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PutMapping("/changepw/{id}")
    public ResponseDto changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequestDto changePasswordRequestDto,  HttpServletRequest request) {
        return userService.changePassword(id,changePasswordRequestDto, request);
    }
    @ApiOperation(value = "계정 삭제", notes = "유저를 삭제한다.(자신 한정)")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return userService.deleteId(id, request);
    }
}
