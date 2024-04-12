package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.response.mypage.GetLessonLikesResponseDto;
import org.springframework.http.ResponseEntity;

public interface MypageService {

    /**
     * 
     * @return  유저가 북마크한 lesson 리스트
     */
    ResponseEntity<? super GetLessonLikesResponseDto> getLessonLikeList(String userId);
}
