package com.sparta.miniproject1.image.repository;

import com.sparta.miniproject1.image.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
