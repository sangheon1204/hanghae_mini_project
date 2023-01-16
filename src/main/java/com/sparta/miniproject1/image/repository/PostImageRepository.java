package com.sparta.miniproject1.image.repository;

import com.sparta.miniproject1.image.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
