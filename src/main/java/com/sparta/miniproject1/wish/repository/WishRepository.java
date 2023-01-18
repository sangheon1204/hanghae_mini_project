package com.sparta.miniproject1.wish.repository;

import com.sparta.miniproject1.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByUserIdAndPostId(Long userId, Long postId);
    Optional<List<Wish>> findAllByPostIdAndState(Long id, boolean state);
    Optional<List<Wish>> findAllByUserIdAndState(Long userId, boolean state);
}
