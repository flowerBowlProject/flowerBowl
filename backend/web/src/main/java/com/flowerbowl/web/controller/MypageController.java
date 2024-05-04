package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.request.mypage.InsertLicenseRequestDto;
import com.flowerbowl.web.dto.response.mypage.*;
import com.flowerbowl.web.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            @AuthenticationPrincipal String userId) {

        return mypageService.getMyLessons(userId);
    }

    @GetMapping("/api/chef/purchasers")
    public ResponseEntity<? super GetPurchasersResponseDto> getPurchasers(
            @AuthenticationPrincipal String userId) {

        return mypageService.getPurchasers(userId);
    }

    @GetMapping("/api/mypage/pays")
    public ResponseEntity<? super GetPaysResponseDto> getPays(
            @AuthenticationPrincipal String userId) {

        return mypageService.getPays(userId);
    }

    @DeleteMapping("/api/mypage/pays/{pay_no}")
    public ResponseEntity<? super DeletePayByUserResponseDto> deletePayByUser(
            @AuthenticationPrincipal String userId,
            @PathVariable(name = "pay_no") Long payNo) {

        return mypageService.deletePayByUser(userId, payNo);

    }

    @DeleteMapping("/api/chef/mypage/pays/{pay_no}")
    public ResponseEntity<? super DeletePayByChefResponseDto> deletePayByChef(
            @AuthenticationPrincipal String userId,
            @PathVariable(name = "pay_no") Long payNo) {

        return mypageService.deletePayByChef(userId, payNo);
    }

    @PostMapping("/api/mypage/chefs")
    public ResponseEntity<? super InsertLicenseResponseDto> insertLicense(
            @AuthenticationPrincipal String userId,
            @RequestBody InsertLicenseRequestDto dto) {

        return mypageService.insertLicense(dto, userId);
    }
}
