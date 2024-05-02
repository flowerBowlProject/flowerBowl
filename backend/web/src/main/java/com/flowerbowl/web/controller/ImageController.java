package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.response.image.ImageResponseDto;
import com.flowerbowl.web.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/content")
    public ResponseEntity<? extends ImageResponseDto> uploadContentImage(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        return imageService.uploadImage(multipartFile, "temp/content");
    }

    @PostMapping("/thumbnail")
    public ResponseEntity<? extends ImageResponseDto> uploadThumbnailImage(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        return imageService.uploadImage(multipartFile, "temp/thumbnail");
    }

    @PostMapping("/profile")
    public ResponseEntity<? extends ImageResponseDto> uploadProfileImage(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        return imageService.uploadImage(multipartFile, "temp/profile");
    }

    @PostMapping("/banner")
    public ResponseEntity<? extends ImageResponseDto> uploadBannerImage(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        return imageService.uploadImage(multipartFile, "temp/banner");
    }

}
