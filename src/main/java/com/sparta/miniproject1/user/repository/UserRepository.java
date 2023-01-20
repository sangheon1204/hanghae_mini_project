package com.sparta.miniproject1.user.repository;

import com.sparta.miniproject1.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndState(String username, boolean state);
    Optional<User> findByKakaoId(Long id);
}