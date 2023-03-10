package com.sparta.miniproject1.user.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sparta.miniproject1.user.dto.KakaoUserInfoDto;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.user.jwt.JwtUtil;
import com.sparta.miniproject1.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public String kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. JWT 토큰 반환
        String createToken =  jwtUtil.createToken(kakaoUser.getUsername(), kakaoUser.getRole());
        // 토큰 던져주기
        return createToken;
    }
    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성 (상세히 적자면 코드 받은 값을 가지고 http 주소로 만들어줌)
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "dca78b23ee6bbb566b637457b88b9de0");
        body.add("redirect_uri", "http://localhost:8080/api/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        log.info("HttpEntity 생성 완료");
        RestTemplate rt = new RestTemplate();
        log.info("Rest Template 통과");
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        log.info("응답 만들기");
        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }
    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );
        //response 받은 것을 해체하기
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        //성분별로 쓰기 좋게 분리하기
        Long id = jsonNode.get("id").asLong();
        String username = jsonNode.get("properties").get("nickname").asText();
        String imgurl = jsonNode.get("properties").get("profile_image").asText();
        String nickname = username;
        return new KakaoUserInfoDto(id, username, nickname, imgurl);
    }
    // 3. 필요시에 회원가입
    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser;
        // 카카오 사용자 id 동일한 id 가진 회원이 있는지 확인
        User sameidUser = userRepository.findByKakaoId(kakaoId).orElse(null);
        if (sameidUser != null) {
            kakaoUser = sameidUser;
            // 기존 회원정보에 카카오 Id 추가
            kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
        } else {

            // 신규 회원가입
            // password: random UUID 무작위 난수로 만들고 이걸 또 인코딩해서 버무린다.
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            //유저 틀 만들기
            kakaoUser = new User(kakaoUserInfo.getUsername(), kakaoId, encodedPassword, kakaoUserInfo.getImgurl());
        }
        //저장!
        userRepository.save(kakaoUser);
        return kakaoUser;
    }
}