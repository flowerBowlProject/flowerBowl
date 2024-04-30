package com.flowerbowl.web.controller;

import com.flowerbowl.web.service.BannerService;
import com.flowerbowl.web.dto.response.banner.BannerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    // 배너조회 // 모든 사용자 가능
    @GetMapping(value = "/api/banners")
//    public void banner(){
    public ResponseEntity<? super BannerResponseDto> banner(){
        return bannerService.banner();
    }
}
