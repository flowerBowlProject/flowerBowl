package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.auth.*;
import com.flowerbowl.web.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    /**
     * 아이디 중복 확인
     */
    ResponseEntity<? super IdCheckResponseDTO> idCheck(IdCheckRequestDTO dto);

    /**
     * 닉네임 중복 확인
     */
    ResponseEntity<? super NicknameCheckResponseDTO> nickNameCheck(NicknameCheckRequestDTO dto);

    /**
     * 인증 메일 반송
     */
    ResponseEntity<? super EmailCertificationSendResponseDTO> emailCertificationSend(EmailCertificationSendRequestDTO dto);

    /**
     * 인증 번호 확인
     */
    ResponseEntity<? super CheckCertificationResponseDTO> checkCertification(CheckCertificationRequestDTO dto);

    /**
     * 회원가입
     */
    ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO dto);

    /**
     * 로그인
     */
    ResponseEntity<? super SignInResponseDTO> signIn(SignInRequestDTO dto);
}
