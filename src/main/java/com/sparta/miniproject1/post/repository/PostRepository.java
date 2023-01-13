package com.sparta.miniproject1.post.repository;

import com.sparta.miniproject1.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
