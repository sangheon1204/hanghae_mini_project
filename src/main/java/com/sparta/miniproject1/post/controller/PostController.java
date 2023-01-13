package com.sparta.miniproject1.post.controller;

import com.sparta.miniproject1.post.dto.PostRequestDto;
import com.sparta.miniproject1.post.dto.ResponseDto;
import com.sparta.miniproject1.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글 작성
    @PostMapping("/posts")
    public ResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(postRequestDto,userDetails.getUser());
    }
    //전체 목록 조회
    @GetMapping("/posts")
    public ResponseDto getPosts() {
        return postService.getPosts();
    }
}
