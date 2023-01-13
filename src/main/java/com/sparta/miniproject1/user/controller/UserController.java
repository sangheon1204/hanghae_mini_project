package com.sparta.miniproject1.user.controller;

import com.sparta.miniproject1.user.dto.LoginRequestDto;
import com.sparta.miniproject1.user.dto.SignupRequestDto;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import com.sparta.miniproject1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

//    @PostMapping("/signup") //이거는 Http 리턴용 Postman으로 하려니 안되서 이렇게 함;;
//    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
//        userService.signup(signupRequestDto);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("/api/user/login"));
//        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//    }
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "success";
    }
//    @PostMapping("/login") //이거는 Http 리턴용 Postman으로 하려니 안되서 이렇게 함;;
//    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        userService.login(loginRequestDto, response);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("/api/user/signup"));
//        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "success";
    }
    // 로그인 한 유저가 메인페이지를 요청할 때 유저의 이름 반환
    @GetMapping("/info")
    public String getUserName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails.getUsername();
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
