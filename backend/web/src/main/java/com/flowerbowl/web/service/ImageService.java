package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.response.image.ImageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public ResponseEntity<? extends ImageResponseDto> uploadImage(MultipartFile multipartFile, String dirName) throws Exception;

}
