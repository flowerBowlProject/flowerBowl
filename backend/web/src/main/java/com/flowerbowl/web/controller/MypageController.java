package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.response.mypage.GetLessonLikeListResponseDto;
import com.flowerbowl.web.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/lesson/likes")
    public ResponseEntity<? super GetLessonLikeListResponseDto> getLessonLikeList(
            @AuthenticationPrincipal String userId) {
        log.info("mypage컨트롤러 작동={}",userId);
        return mypageService.getLessonLikeList(userId);
    }
}
