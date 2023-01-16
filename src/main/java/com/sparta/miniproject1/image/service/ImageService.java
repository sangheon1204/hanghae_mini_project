package com.sparta.miniproject1.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.sparta.miniproject1.image.dto.ImageResponseDto;
import com.sparta.miniproject1.image.entity.PostImage;
import com.sparta.miniproject1.image.repository.PostImageRepository;
import com.sparta.miniproject1.image.repository.ProfileImageRepository;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.post.repository.PostRepository;
import com.sparta.miniproject1.s3.CommonUtils;
import com.sparta.miniproject1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3Client amazonS3Client;
    private final PostImageRepository postImageRepository;

    private final ProfileImageRepository profileImageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final PostRepository postRepository;

    //상품의 이미지 파일 업로드
    @Transactional
    public ImageResponseDto uploadFile(Long id, MultipartFile multipartFile, User user) throws IOException {
        String imageUrl = null;

        //s3에 저장하고 이미지 url 받아오기
        if (!multipartFile.isEmpty()) {
            String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            objectMetadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imageUrl = amazonS3Client.getUrl(bucketName, fileName).toString();
            // 해당 게시글 찾기
            Post post = postRepository.findById(id).orElseThrow(
                    ()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
            );
            //객체 저장
            PostImage postImage = new PostImage(imageUrl,user,post);
            postImageRepository.save(postImage);
            return new ImageResponseDto(multipartFile,imageUrl);
        }
        return null;
    }
    //회원의 프로필 사진 업로드
//    @Transactional
//    public ImageResponseDto uploadProfile(MultipartFile multipartFile) {
//        return new ImageResponseDto();
//    }
}
