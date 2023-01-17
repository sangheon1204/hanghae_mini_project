package com.sparta.miniproject1.image.controller;

import com.sparta.miniproject1.image.dto.ImageResponseDto;
import com.sparta.miniproject1.image.service.ImageService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Api(tags = "image")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ImageController {

    private final ImageService imageService;

    //프로필 사진 업로드
    @ApiOperation(value = "프로필 업로드", notes = "프로필 이미지를 s3에 저장한다.")
    @PostMapping("/files/profile")
    public ImageResponseDto uploadProfile(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        log.info(multipartFile.getName());
        return imageService.uploadFile(multipartFile);
    }

    //상품 이미지 업로드
    //로그인 상태로 하는 것이기 때문에 인증 인가 필요
    @ApiOperation(value = "상품 이미지 업로드", notes = "상품 이미지를 s3에 저장한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/files/image")
    public ImageResponseDto uploadImage(@RequestParam(value= "file") MultipartFile multipartFile) throws IOException {
        return imageService.uploadFile(multipartFile);
    }
}
