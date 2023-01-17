package com.sparta.miniproject1.wish.repository;

import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Wish findByUserIdAndPostId(Long userId, Long postId);
    List<Wish> findAllByPostId(Long id);
}
