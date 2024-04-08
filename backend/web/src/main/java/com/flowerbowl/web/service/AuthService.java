package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.auth.*;
import com.flowerbowl.web.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    /**
     * 아이디 중복 확인
     */
    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);

    /**
     * 닉네임 중복 확인
     */
    ResponseEntity<? super NicknameCheckResponseDto> nickNameCheck(NicknameCheckRequestDto dto);

    /**
     * 인증 메일 반송
     */
    ResponseEntity<? super EmailCertificationSendResponseDto> emailCertificationSend(EmailCertificationSendRequestDto dto);

    /**
     * 인증 번호 확인
     */
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);

    /**
     * 회원가입
     */
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    /**
     * 로그인
     */
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
