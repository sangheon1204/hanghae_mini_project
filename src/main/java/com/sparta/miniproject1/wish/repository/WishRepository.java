package com.sparta.miniproject1.wish.repository;

import com.sparta.miniproject1.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {
}
