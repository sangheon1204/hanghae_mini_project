package com.sparta.miniproject1.post.controller;

import com.sparta.miniproject1.post.dto.PageResponseDto;
import com.sparta.miniproject1.post.dto.PostRequestDto;
import com.sparta.miniproject1.post.dto.PostResponseDto;
import com.sparta.miniproject1.post.dto.ResponseDto;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.service.PostService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = {"Post"})
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글 작성
    @ApiOperation(value = "게시글 추가(상품등록)", notes = "게시글 목록에 상품을 등록한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/posts")
    public ResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto,userDetails.getUser());
    }
    //전체 목록 조회
    @ApiOperation(value = "게시글 목록 조회", notes = "등록된 게시글 목록 전체를 조회한다.")
    @GetMapping("/posts")
    public Page<PageResponseDto> getPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc,
            @RequestParam("sortBy") String sortBy
    ) {
        return postService.getPosts(page-1,size,isAsc,sortBy);
    }
    //id로 게시글 단건 조회
    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    //id로 게시글 업데이트
    @ApiOperation(value = "게시글 수정", notes = "게시글을 게시글의 id로 찾아 수정한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PatchMapping("/posts/{id}")
    public ResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id,request,userDetails.getUser());
    }

    //id로 게시물 삭제
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 게시글의 id로 찾아 삭제한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @DeleteMapping("/posts/{id}")
    public ResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id,userDetails.getUser());
    }

    //상품 찜하기 기능
    @ApiOperation(value = "게시물 찜", notes = "게시물을 찜하고 취소할 수 있다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PatchMapping("/posts/like/{id}")
    public ResponseDto wishPost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.likePost(id,userDetails.getUser());
    }
}
