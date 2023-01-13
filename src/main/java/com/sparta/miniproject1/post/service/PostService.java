package com.sparta.miniproject1.post.service;

import com.sparta.miniproject1.post.dto.PostRequestDto;
import com.sparta.miniproject1.post.dto.ResponseDto;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //게시글 작성하기
    @Transactional
    public ResponseDto createPost(PostRequestDto postRequestDto, User user) {
        // 게시글 객체 생성
        Post post = new Post(postRequestDto,user);
        //게시글 객체 db에 저장
        postRepository.save(post);
        //msg: 게시글 작성완료! 반환
        return new ResponseDto("게시글 작성 완료!");
    }

    public ResponseDto getPosts() {

    }
}