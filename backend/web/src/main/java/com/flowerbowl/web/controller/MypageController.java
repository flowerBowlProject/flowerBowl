package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.response.mypage.*;
import com.flowerbowl.web.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/api/mypage/lesson/likes")
    public ResponseEntity<? super GetLessonLikesResponseDto> getLessonLikes(
            @AuthenticationPrincipal String userId) {

        return mypageService.getLessonLikes(userId);
    }

    @GetMapping("/api/mypage/recipe/likes")
    public ResponseEntity<? super GetRecipeLikesResponseDto> getRecipeLikes(
            @AuthenticationPrincipal String userId) {

        return mypageService.getRecipeLikes(userId);
    }

    @GetMapping("/api/mypage/lessons")
    public ResponseEntity<? super GetPayLessonResponseDto> getPayLesson(
            @AuthenticationPrincipal String userId) {

        return mypageService.getPayLessons(userId);
    }

    @GetMapping("/api/mypage/recipes")
    public ResponseEntity<? super GetMyRecipeResponseDto> getMyRecipes(
            @AuthenticationPrincipal String userId) {

        return mypageService.getMyRecipes(userId);
    }

    @GetMapping("/api/chef/lessons")
    public ResponseEntity<? super GetMyLessonResponseDto> getMyLessons(
            @AuthenticationPrincipal String userId){

        return mypageService.getMyLessons(userId);
    }
}
