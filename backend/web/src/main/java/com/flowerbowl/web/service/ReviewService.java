package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.review.InsertReviewRequestDto;
import com.flowerbowl.web.dto.request.review.PatchReviewRequestDto;
import com.flowerbowl.web.dto.response.review.*;
import org.springframework.http.ResponseEntity;

public interface ReviewService {


    /**
     * @return 리뷰 저장
     */
    ResponseEntity<? super InsertReviewResponseDto> reviewInsert(InsertReviewRequestDto dto, String userId);


    /**
     * @return 리뷰 작성 가능한 클래스 목록
     */
    ResponseEntity<? super AvailableReviewsResponseDto> availableReviewList(String userId);

    /**
     * List<Object[]>를 반환하는 list은 view 테이블 만들면 더 좋을 듯
     *
     * @return 작성한 리뷰 리스트 목록
     */
    ResponseEntity<? super WrittenReviewsResponseDto> writtenReviews(String userId);

    /**
     * @return 리뷰 수정
     */
    ResponseEntity<? super PatchReviewResponseDto> reviewUpdate(PatchReviewRequestDto dto, String userId, Long reviewNo);


    /**
     * @return 리뷰 삭제
     */
    ResponseEntity<? super DeleteReviewResponseDto> reviewDelete(String userId, Long reviewNo);

    /**
     * @return 리뷰 상세
     */
    ResponseEntity<? super GetReviewResponseDto> getReview(String userId, Long reviewNo);
}
