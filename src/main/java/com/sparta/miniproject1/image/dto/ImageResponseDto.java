package com.sparta.miniproject1.image.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Getter
@NoArgsConstructor
public class ImageResponseDto {
    private String originalFileName;
    private String fullName;
    private String url;
    private String mediaCode;

    public ImageResponseDto(MultipartFile multipartFile, String imageUrl) {
        this.originalFileName = multipartFile.getOriginalFilename();
        this.fullName = multipartFile.getName();
        this.url = imageUrl;
        this.mediaCode = "IMAGE";
    }
    public ImageResponseDto(String imageUrl) {
        this.originalFileName = "fleamarket";
        this.fullName = "png";
        this.url = imageUrl;
        this.mediaCode = "IMAGE";
    }
}
