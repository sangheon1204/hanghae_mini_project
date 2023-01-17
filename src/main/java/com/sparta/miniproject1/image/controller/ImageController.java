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

    //이미지 파일 업로드(상품)
    @ApiOperation(value = "이미지 파일 업로드", notes = "이미지 파일을 업로드한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/files/{id}")
    public ImageResponseDto uploadFile(@PathVariable Long id,@RequestParam(value = "file") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return imageService.uploadFile(id, multipartFile, userDetails.getUser());
    }

    //프로필 이미지 업로드
    @ApiOperation(value = "프로필 사진 업로드", notes = "프로필 사진을 업로드합니다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/profiles")
    public ImageResponseDto uploadProfile(@RequestParam("profile") MultipartFile multipartFile,@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        return imageService.uploadProfile(multipartFile, userDetails.getUser());
    }
}
