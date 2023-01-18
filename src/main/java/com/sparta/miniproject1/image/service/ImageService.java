package com.sparta.miniproject1.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.sparta.miniproject1.image.dto.ImageResponseDto;
import com.sparta.miniproject1.s3.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final AmazonS3Client amazonS3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    //프로필 이미지를 profile 폴더에 저장
    public ImageResponseDto uploadProfile(MultipartFile multipartFile) throws IOException {
        return saveAndReturn(multipartFile, "profile/");
    }
    @Transactional
    //상품 이미지를 image 폴더에 저장
    public ImageResponseDto uploadImage(MultipartFile multipartFile) throws IOException {
        return saveAndReturn(multipartFile, "image/");
    }

    public ImageResponseDto saveAndReturn(MultipartFile multipartFile, String key) throws IOException{
        String imageUrl;
        //s3에 저장하고 이미지 url 받아오기
        if (!multipartFile.isEmpty()) {
            String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            objectMetadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

            //s3에 저장
            amazonS3Client.putObject(new PutObjectRequest(bucketName, key+fileName, byteArrayIs, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imageUrl = amazonS3Client.getUrl(bucketName, key + fileName).toString();
            return new ImageResponseDto(multipartFile, imageUrl);
        }
        return null;
    }
}
