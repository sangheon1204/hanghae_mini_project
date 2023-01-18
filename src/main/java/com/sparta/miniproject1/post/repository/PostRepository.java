package com.sparta.miniproject1.post.repository;

import com.sparta.miniproject1.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndUserId(Long id,Long userId);

    Optional<List<Post>> findByUserId(Long userId);

    Optional<Page<Post>> findAllByState(Pageable pageable, boolean state);
}
