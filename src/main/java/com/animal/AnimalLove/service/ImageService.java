package com.animal.AnimalLove.service;

import com.animal.AnimalLove.data.entity.Image;
import com.animal.AnimalLove.data.entity.Post;
import com.animal.AnimalLove.data.repository.ImageRepository;
import com.animal.AnimalLove.data.repository.PostRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final Cloudinary cloudinary;

    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    public Image uploadImage(Long savedPostId, MultipartFile file) throws IOException {
        // Cloudinary에 이미지 업로드
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // Cloudinary에서 반환된 URL 및 public_id 저장
        String url = (String) uploadResult.get("secure_url");
        String publicId = (String) uploadResult.get("public_id");

        // post 객체 조회
        Post post = postRepository.findById(savedPostId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        log.info("[postRepository.findById] 값 : {}, {}",post.getPostId(), post.getComments());

        // 이미지 정보를 데이터베이스에 저장
        return imageRepository.save(Image.builder()
                .publicId(publicId)
                .url(url)
                .post(post)
                .build()
        );
    }
}