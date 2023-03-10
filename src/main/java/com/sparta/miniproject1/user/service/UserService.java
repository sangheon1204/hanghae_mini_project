package com.sparta.miniproject1.user.service;

import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.comment.repository.CommentRepository;

import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.repository.PostRepository;

import com.sparta.miniproject1.user.dto.*;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.user.jwt.JwtUtil;
import com.sparta.miniproject1.user.repository.UserRepository;

import com.sparta.miniproject1.wish.entity.Wish;
import com.sparta.miniproject1.wish.repository.WishRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final WishRepository wishRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    // ADMIN_TOKEN(추후에 써먹을 것)
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    String pt = "^[a-z\\\\d`~!@#$%^&*()-_=+]{4,10}$";
    String ptt = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,15}$";


    @Transactional
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        //이름, 비밀번호 대조를 위해 값을 뽑아놓음
        String username = signupRequestDto.getUsername();
        String pwcheck = signupRequestDto.getPassword();
        String passwordCheck = signupRequestDto.getPasswordCheck();
        String imgurl=signupRequestDto.getImageResponseDto().getUrl();
        if(imgurl==null){
        imgurl = "https://myminiprojectbucket.s3.ap-northeast-2.amazonaws.com/profile/user.png";
        }

        //username 조건 확인
        if (!Pattern.matches(pt, username)) {

            throw new IllegalArgumentException(
                    "아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 에 한해서 구성되어야합니다.");
        }
        //비밀번호 조건 확인
        if (!Pattern.matches(ptt, pwcheck)) {
            throw new IllegalArgumentException(
                    "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 에 한해서 구성되어야합니다.");
        }
        if (!pwcheck.matches(passwordCheck)) {
            throw new IllegalArgumentException(
                    "비밀번호 입력을 다시 확인해주세요.");
        }
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsernameAndState(username, true);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //저장을 바로 하면 안되고 encoding해서 저장하기
        String password = passwordEncoder.encode(pwcheck);

        //등록등록
        User user = new User(signupRequestDto,password,imgurl);
        userRepository.save(user);
        return new ResponseDto("가입 완료");
    }


    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //이름, 비밀번호 대조를 위해 값을 뽑아놓음
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsernameAndState(username, true).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //토큰을 생성해서 유저에게 줌
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new ResponseDto(user.getNickname() + " 님 로그인 완료");
    }
    @Transactional
    public UserInfoDto getInfo(HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        // 토큰에서 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user =  userRepository.findByUsernameAndState(claims.getSubject(), true).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        return new UserInfoDto(user.getUsername(),user.getNickname(),user.getImgurl());
    }
    @Transactional
    public ResponseDto changePassword(String username, ChangePasswordRequestDto changePasswordRequestDto, User user) {
        Optional<User> found = userRepository.findByUsernameAndState(username, true);
        if (found.isEmpty() || !found.get().isState()) {    //삭제된 상태에서 비밀번호 변경 방지
            throw new IllegalArgumentException("사용자가 없습니다.");
        }
        if(!user.getUsername().equals(username)){ //대리 삭제 방지
            throw new IllegalArgumentException("다른 아이디 삭제는 안됩니다.");
        }
        //요청받은 비번 값 확인
        String npw = changePasswordRequestDto.getPassword();
        if (!Pattern.matches(ptt, npw)) {
            throw new IllegalArgumentException(
                    "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 에 한해서 구성되어야합니다.");
        }
        //저장을 바로 하면 안되고 encoding해서 저장하기
        String npww = passwordEncoder.encode(npw);
        user.update(npww);
        return new ResponseDto("비밀번호가 변경되었습니다");
    }

    @Transactional  // soft delete 이고 게시글 댓글도 지움
    public ResponseDto softDeleteId(String username, User user) {
        Optional<User> found = userRepository.findByUsernameAndState(username, true);
        if (found.isEmpty() || !found.get().isState()) {    //삭제된 상태에서 다시 삭제 방지
            throw new IllegalArgumentException("사용자가 없습니다.");
        }
        if(!user.getUsername().equals(username)){ //대리 삭제 방지
            throw new IllegalArgumentException("다른 아이디 삭제는 안됩니다.");
        }
        // 댓글 / 대댓글 삭제
        List<Comment> commentList = commentRepository.findByUserId(user.getId()).orElse(new ArrayList<>());
        commentList.forEach(Comment::deleteComment);

        // 찜 상품 삭제
        List<Wish> wishList = wishRepository.findAllByUserIdAndState(user.getId(), true).orElse(new ArrayList<>());
        wishList.forEach(Wish::updateWish);

        // 게시글 삭제
        List<Post> postList = postRepository.findByUserId(user.getId()).orElse(new ArrayList<>());
        postList.forEach(Post::deletePost);

        // 삭제를 database -> state true->false (휴먼계정)
        found.get().deleteUser();
        return new ResponseDto("아이디 삭제 완료");
    }

}