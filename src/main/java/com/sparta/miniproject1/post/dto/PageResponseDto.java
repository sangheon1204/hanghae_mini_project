package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageResponseDto {
    private Long id;
    private String name;
    private String description;
    private String price;
    private String category;
    private String imageUrl;

    public static PageResponseDto toDto(final Post post) {
        return PageResponseDto.builder()
                .id(post.getId())
                .name(post.getName())
                .description(post.getDescription())
                .price(post.getPrice())
                .category(post.getCategory())
                .imageUrl(post.getImageUrl())
                .build();
    }
}
