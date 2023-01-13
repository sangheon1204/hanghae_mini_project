package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageResponseDto {
    private String name;
    private String description;
    private int price;
    private String category;

    public static PageResponseDto toDto(final Post post) {
        return PageResponseDto.builder()
                .name(post.getName())
                .description(post.getDescription())
                .price(post.getPrice())
                .category(post.getCategory())
                .build();
    }
}
