package com.flowerbowl.web.controller;


import com.flowerbowl.web.dto.request.review.InsertReviewRequestDto;
import com.flowerbowl.web.dto.request.review.PatchReviewRequestDto;
import com.flowerbowl.web.dto.response.review.*;
import com.flowerbowl.web.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<? super InsertReviewResponseDto> reviewInsert(
            @RequestBody @Valid InsertReviewRequestDto dto,
            @AuthenticationPrincipal String userId) {

        return reviewService.reviewInsert(dto, userId);
    }

    @GetMapping("/list")
    public ResponseEntity<? super AvailableReviewsResponseDto> ableReviewList(
            @AuthenticationPrincipal String userId) {

        return reviewService.availableReviewList(userId);
    }

    @GetMapping
    public ResponseEntity<? super WrittenReviewsResponseDto> writtenReviews(
            @AuthenticationPrincipal String userId) {

        return reviewService.writtenReviews(userId);
    }

    @PatchMapping("/{review_no}")
    public ResponseEntity<? super PatchReviewResponseDto> reviewUpdate(
            @RequestBody @Valid PatchReviewRequestDto dto,
            @AuthenticationPrincipal String userId,
            @PathVariable(name = "review_no") Long reviewNo) {

        return reviewService.reviewUpdate(dto, userId, reviewNo);
    }

    @DeleteMapping("/{review_no}")
    public ResponseEntity<? super DeleteReviewResponseDto> reviewDelete(
            @PathVariable(name = "review_no") Long reviewNo) {

        return reviewService.reviewDelete(reviewNo);
    }
}
