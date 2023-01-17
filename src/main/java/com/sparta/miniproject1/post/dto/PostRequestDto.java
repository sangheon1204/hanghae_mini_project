package com.sparta.miniproject1.post.dto;

import com.sparta.miniproject1.image.dto.ImageResponseDto;
import com.sparta.miniproject1.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String name;
    private String description;
    private String price;
    private String category;
    private ImageResponseDto imageResponseDto;
}
