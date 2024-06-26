package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.mypage.InsertLicenseRequestDto;
import com.flowerbowl.web.dto.response.ResponseDto;
import com.flowerbowl.web.dto.response.mypage.*;
import org.springframework.http.ResponseEntity;

public interface MypageService {

    /**
     * @return 유저가 북마크한 lesson 리스트
     */
    ResponseEntity<? super GetLessonLikesResponseDto> getLessonLikes(String userId);

    /**
     * @return 유저가 북마크한 recipe 리스트
     */
    ResponseEntity<? super GetRecipeLikesResponseDto> getRecipeLikes(String userId);

    /**
     * @return 수강 클래스 조회
     */
    ResponseEntity<? super GetPayLessonResponseDto> getPayLessons(String userId);

    /**
     * @return 창작 레시피 조회
     */
    ResponseEntity<? super GetMyRecipeResponseDto> getMyRecipes(String userId);


    /**
     * @return 창작 클래스 조회
     */
    ResponseEntity<? super GetMyLessonResponseDto> getMyLessons(String userId);


    /**
     * @return 구매자 목록 반환
     */
    ResponseEntity<? super GetPurchasersResponseDto> getPurchasers(String userId);

    /**
     * @return 결제 내역 조회
     */
    ResponseEntity<? super GetPaysResponseDto> getPays(String userId);

    /**
     * @return 유저가 결제 취소
     */
    ResponseEntity<? super DeletePayByUserResponseDto> deletePayByUser(String userId, Long payNo);


    /**
     * @return 쉐프가 결제 취소
     */
    ResponseEntity<? super DeletePayByChefResponseDto> deletePayByChef(String userId, Long payNo);

    /**
     * @return 쉐프 신청
     */
    ResponseEntity<? super InsertLicenseResponseDto> insertLicense(InsertLicenseRequestDto dto, String userId);
}
