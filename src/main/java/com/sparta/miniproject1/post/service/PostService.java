package com.sparta.miniproject1.post.service;

import com.sparta.miniproject1.post.Comments;
import com.sparta.miniproject1.post.dto.PostRequestDto;
import com.sparta.miniproject1.post.dto.PostResponseDto;
import com.sparta.miniproject1.post.dto.ResponseDto;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.repository.PostRepository;
import com.sparta.miniproject1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Post post = new Post(postRequestDto, user);
        //게시글 객체 db에 저장
        postRepository.save(post);
        //msg: 게시글 작성완료! 반환
        return new ResponseDto("게시글 작성 완료!");
    }

    //전체 게시글 조회하기
    public Page<Post> getPosts(int page, int size, boolean isAsc, String sortBy) {
        //페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy );
        Pageable pageable = PageRequest.of(page,size,sort);
        //게시글 전체 조회
        Page<Post> posts;
        posts = postRepository.findAll(pageable);
        return posts;
    }

//    게시물 id로 조회하기
    public PostResponseDto getPost(Long id) {
        //id로 게시물 조회하기
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );
        return new PostResponseDto(post,new Comments(post.getCommentList()));
    }
}