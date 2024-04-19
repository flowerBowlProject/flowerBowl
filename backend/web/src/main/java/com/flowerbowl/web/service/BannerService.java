package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.response.banner.BannerResponseDto;
import org.springframework.http.ResponseEntity;

public interface BannerService {
    ResponseEntity<? super BannerResponseDto> banner();
}
