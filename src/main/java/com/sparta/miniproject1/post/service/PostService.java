package com.sparta.miniproject1.post.service;

import com.sparta.miniproject1.comment.dto.CommentDto;
import com.sparta.miniproject1.comment.entity.Comment;
import com.sparta.miniproject1.comment.repository.CommentRepository;
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
import com.sparta.miniproject1.user.repository.UserRepository;
import com.sparta.miniproject1.wish.entity.Wish;
import com.sparta.miniproject1.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final WishRepository wishRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    //게시글 작성하기
    @Transactional
    public ResponseDto createPost(PostRequestDto postRequestDto, User user)  {
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
        //오름차순, 내림차순 정하기
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy );
        Pageable pageable = PageRequest.of(page,size,sort);
        //게시글 전체 조회
        Page<Post> posts;
        posts = postRepository.findAllByState(pageable, true).orElse(new PageImpl<>(new ArrayList<>()));
        // posts-> postsDto로 전환
        Page<PageResponseDto> pageResponseDto = posts.map(PageResponseDto :: toDto);
        return pageResponseDto;
    }

//    게시물 id로 조회하기
    @Transactional
    public PostResponseDto getPost(Long id, User user) {
        //id로 게시물 조회하기
        Post post = findPostByid(id);
        //게시글에 딸린 댓글 리스트를 가지고 있는 객체 생성
        Comments comments = new Comments(commentRepository.findAllByPostIdAndIsReplyAndStateOrderByCreatedAtDesc(post.getId(), false, true).orElse(new ArrayList<>()));
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment comment : comments.getComments()) {
            //대댓글 찾기
            Replies replies = new Replies(commentRepository.findAllByReferenceIdAndState(comment.getId(), true).orElse(new ArrayList<>()));
            List<ReplyDto> replyDtoList = replies.getReplies().stream().map(ReplyDto :: new).collect(Collectors.toList());
            commentDtoList.add(new CommentDto(comment.getId(),comment.getComment(),replyDtoList));
        }
        boolean state= false;
        if(post.getUserId() == user.getId()) {
            state = true;
            return new PostResponseDto(post,commentDtoList,user,state);
        }
        return new PostResponseDto(post,commentDtoList,user,state);
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

    //게시물 soft delete
    @Transactional
    public ResponseDto softDeletePost(Long id, User user) {
        //id로 게시물 찾기
        Post post = findPostByid(id,user);

        //대댓글 삭제 //댓글 삭제
        List<Comment> commentList = commentRepository.findAllByPostId(post.getId()).orElse(new ArrayList<>());   //댓글이랑 대댓글 포함
        commentList.forEach(Comment::deleteComment);

        //찜 삭제
        List<Wish> wishList = wishRepository.findAllByPostIdAndState(post.getId(), true).orElse(new ArrayList<>());
        wishList.forEach(Wish::updateWish);

        //게시글 삭제
        post.deletePost();

        return new ResponseDto("삭제 완료.");
    }

    //게시물 찜하기/ 찜 취소하기
    @Transactional
    public ResponseDto likePost(Long id, User user) {
        // id로 게시글 찾기
        Post post = findPostByid(id);
        // 유저가 찜했는지 여부 확인
        Optional<Wish> wish = wishRepository.findByUserIdAndPostId(user.getId(), post.getId());

        //찜 했던 기록이 없으면 추가
        if(wish.isEmpty()) {
            Wish newWish = new Wish(user, post);
            wishRepository.save(newWish);
            return new ResponseDto("찜하기");
        }
        //찜을 하고 있는 상태면 삭제 상태로 변경
        wish.get().updateWish();
        if(wish.get().isState()) {
            return new ResponseDto("찜하기 취소");
        }
        //찜을 했었지만 취소했던 상태이면 다시 복원
        return new ResponseDto("찜하기");
    }

    public Post findPostByid(Long id, User user){
        if(user.getRole() == UserRoleEnum.ADMIN) {
            Post post = postRepository.findById(id).orElseThrow(
                    ()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
            );
            return post;
        }
        Post post = postRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
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