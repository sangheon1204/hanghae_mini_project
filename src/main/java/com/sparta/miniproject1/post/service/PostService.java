package com.sparta.miniproject1.post.service;

import com.sparta.miniproject1.comment.dto.CommentDto;
import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.post.Comments;
import com.sparta.miniproject1.post.Replies;
import com.sparta.miniproject1.post.dto.PageResponseDto;
import com.sparta.miniproject1.post.dto.PostRequestDto;
import com.sparta.miniproject1.post.dto.PostResponseDto;
import com.sparta.miniproject1.post.dto.ResponseDto;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.repository.PostRepository;
import com.sparta.miniproject1.post.dto.ReplyDto;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.user.entity.UserRoleEnum;
import com.sparta.miniproject1.wish.entity.Wish;
import com.sparta.miniproject1.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final WishRepository wishRepository;

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
    @Transactional
    public Page<PageResponseDto> getPosts(int page, int size, boolean isAsc, String sortBy) {
        //페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy );
        Pageable pageable = PageRequest.of(page,size,sort);
        //게시글 전체 조회
        Page<Post> posts;
        posts = postRepository.findAll(pageable);
        Page<PageResponseDto> pageResponseDto = posts.map(PageResponseDto :: toDto);
        return pageResponseDto;
    }

//    게시물 id로 조회하기
    @Transactional
    public PostResponseDto getPost(Long id) {
        //id로 게시물 조회하기
        Post post = findPostByid(id);
        //게시글에 딸린 댓글 리스트를 가지고 있는 객체 생성
        Comments comments = new Comments(post.getCommentList());
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment comment : comments.getComments()) {
            Replies replies = new Replies(comment.getReplyList());
            List<ReplyDto> replyDtoList = replies.getReplies().stream().map(ReplyDto :: new).collect(Collectors.toList());
            commentDtoList.add(new CommentDto(comment.getId(),comment.getComment(),replyDtoList));
        }
        return new PostResponseDto(post,commentDtoList);
    }

    //게시물 수정
    @Transactional
    public ResponseDto updatePost(Long id, PostRequestDto request, User user) {
        //id로 게시물 찾기
        Post post = findPostByid(id, user);
        //게시물 수정
        post.update(request);
        return new ResponseDto("수정 완료.");
    }

    //게시물 삭제
    public ResponseDto deletePost(Long id, User user) {
        //id로 게시물 찾기
        Post post = findPostByid(id,user);
        //게시물 삭제
        postRepository.delete(post);
        return new ResponseDto("삭제 완료.");
    }

    //게시물 찜하기/ 찜 취소하기
    public ResponseDto likePost(Long id, User user) {
        // id로 게시글 찾기
        Post post = findPostByid(id);
        // 유저가 찜했는지 여부 확인
        Wish wish =wishRepository.findByUserAndPost(user,post);
        if(wish!= null) {
            //이미 찜을 한 경우
            //찜 취소하면서 저장된 객체 삭제
            wish.changeState();
            return new ResponseDto("찜하기 취소");
        }
        Wish newWish = new Wish(user, post);
        wishRepository.save(newWish);
        return new ResponseDto("찜하기");
    }

    public Post findPostByid(Long id, User user){
        if(user.getRole() == UserRoleEnum.ADMIN) {
            Post post = postRepository.findById(id).orElseThrow(
                    ()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
            );
            return post;
        }
        Post post = postRepository.findByIdAndUser(id,user).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );
        return post;
    }
    //게시물 단건 조회할 때 사용
    public Post findPostByid(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );
        return post;
    }
}