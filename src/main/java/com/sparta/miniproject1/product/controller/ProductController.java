package com.sparta.miniproject1.product.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //게시글 작성
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postSerivce.createPost(postRequestDto,userDetails.getUser());
    }
    //전체 목록 조회
    @GetMapping("/posts")
    public
}
