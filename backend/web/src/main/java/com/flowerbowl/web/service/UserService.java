package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.user.PatchProfileRequestDto;
import com.flowerbowl.web.dto.response.user.GetUserInfoResponseDto;
import com.flowerbowl.web.dto.response.user.PatchProfileResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * @return 유저 정보
     */
    ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(String userId);


    /**
     * 
     * @return 유저 정보 수정
     */
    ResponseEntity<? super PatchProfileResponseDto> putUserProfile(PatchProfileRequestDto dto, String userId);
}
