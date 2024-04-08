package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.response.mypage.GetLessonLikeListResponseDto;
import com.flowerbowl.web.dto.response.mypage.GetRecipeLikeListResponseDto;
import org.springframework.http.ResponseEntity;

public interface MypageService {

    /**
     * 
     * @return  유저가 북마크한 lesson 리스트
     */
    ResponseEntity<? super GetLessonLikeListResponseDto> getLessonLikeList(String userId);


    /**
     *
     * @return 유저가 북마크한 recipe 리스트
     */
    ResponseEntity<? super GetRecipeLikeListResponseDto> getRecipeLikeList(String userId);
}
