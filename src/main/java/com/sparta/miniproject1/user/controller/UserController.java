package com.sparta.miniproject1.user.controller;

import com.sparta.miniproject1.user.dto.ChangePasswordRequestDto;

import com.sparta.miniproject1.user.dto.LoginRequestDto;
import com.sparta.miniproject1.user.dto.SignupRequestDto;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import com.sparta.miniproject1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "가입 완료";
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "로그인 완료";
    }
    // 로그인 한 유저가 메인페이지를 요청할 때 유저의 이름 반환
    @GetMapping("/info")
    public String getUserName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails.getUsername();
    }
    //비밀번호 변경....이건 회의하고 넣을지 말지 결정하자.
    @PutMapping("/changepw/{id}")
    public String changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequestDto changePasswordRequestDto, HttpServletRequest request) {
        userService.updatePassword(id, changePasswordRequestDto, request);
        // 응답 보내기 (업데이트된 상품 id)
        return "변경 완료";
    }

    @GetMapping("/forbidden")
    public ModelAndView getForbidden() {
        return new ModelAndView("forbidden");
    }

    @PostMapping("/forbidden")
    public ModelAndView postForbidden() {
        return new ModelAndView("forbidden");
    }
}
