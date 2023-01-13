package com.sparta.miniproject1.user.service;



import com.sparta.miniproject1.user.dto.ChangePasswordRequestDto;
import com.sparta.miniproject1.user.dto.ChangePasswordResponseDto;

import com.sparta.miniproject1.user.dto.LoginRequestDto;
import com.sparta.miniproject1.user.dto.SignupRequestDto;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.user.entity.UserRoleEnum;
import com.sparta.miniproject1.user.jwt.JwtUtil;
import com.sparta.miniproject1.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    String pt = "^[a-z\\\\d`~!@#$%^&*()-_=+]{4,10}$";
    String ptt = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,15}$";


    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        //이름, 비밀번호 대조를 위해 값을 뽑아놓음
        String username = signupRequestDto.getUsername();

        String pwcheck = signupRequestDto.getPassword();
        String passwordCheck = signupRequestDto.getPasswordCheck();

        //username 확인
        if (!Pattern.matches(pt, username)) {

            throw new IllegalArgumentException(
                    "아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 되어야합니다.");
        }

        if (!Pattern.matches(ptt, pwcheck)) {
            throw new IllegalArgumentException(
                    "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 되어야합니다.");
        }
        if (!pwcheck.matches(passwordCheck)) {
            throw new IllegalArgumentException(
                    "비밀번호 입력을 다시 확인해주세요.");

        }
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        //역할군 지정 위해서 role 변수 만듦 + 초기값은 USER로
        UserRoleEnum role = UserRoleEnum.USER;
        //토큰 확인!
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        //저장을 바로 하면 안되고 encoding해서 저장하기
        String password = passwordEncoder.encode(pwcheck);

        //등록등록
        User user = new User(username, password, role);
        userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //이름, 비밀번호 대조를 위해 값을 뽑아놓음
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        }
        //토큰을 생성해서 유저에게 줌
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    @Transactional
    public ChangePasswordResponseDto updatePassword(Long id, ChangePasswordRequestDto changePasswordRequestDto, HttpServletRequest request) {
//        // Request에서 Token 가져오기
//        String token = jwtUtil.resolveToken(request);
//        // 토큰에서 사용자 정보 가져오기
//        Claims claims = jwtUtil.getUserInfoFromToken(token);
        // 해당 사용자 찾기
        User user =  userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        //저장을 바로 하면 안되고 encoding해서 저장하기
        String npw = passwordEncoder.encode(changePasswordRequestDto.getPassword());
        user.update(npw);
         return new ChangePasswordResponseDto(user);
        //변경하려는 비밀번호를 encoding해서 대조
//        String pwc = passwordEncoder.encode(changePasswordRequestDto.getPassword());
//        //맞다면(이건 나중에 할거)
//        if(pwc.matches(user.getPassword())){
//            user.update(changePasswordRequestDto);
//            return new ChangePasswordResponseDto(user);
//        }else{
//            return null;
//        }



    }
}