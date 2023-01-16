package com.sparta.miniproject1.image.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@Getter
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
}
