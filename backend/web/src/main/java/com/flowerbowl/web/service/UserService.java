package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.user.FindUserIdRequestDto;
import com.flowerbowl.web.dto.request.user.FindUserPwRequestDto;
import com.flowerbowl.web.dto.request.user.PatchProfileRequestDto;
import com.flowerbowl.web.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * @return 유저 정보
     */
    ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(String userId);


    /**
     * @return 유저 정보 수정
     */
    ResponseEntity<? super PatchProfileResponseDto> patchUserProfile(PatchProfileRequestDto dto, String userId);

    /**
     *
     * @return 회원 탈퇴
     */
    ResponseEntity<? super PatchWdResponseDto> patchWd(String userId);

    /**
     * 
     * @return 아이디 찾기
     */
    ResponseEntity<? super FindUserIdResponseDto> findId(FindUserIdRequestDto dto);


    /**
     * 
     * @return 비밀번호 찾기
     */
    ResponseEntity<? super FindUserPwResponseDto> findPw(FindUserPwRequestDto dto);
}
