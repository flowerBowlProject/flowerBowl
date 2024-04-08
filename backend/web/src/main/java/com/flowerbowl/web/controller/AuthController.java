package com.flowerbowl.web.controller;


import com.flowerbowl.web.dto.request.auth.*;
import com.flowerbowl.web.dto.response.auth.*;
import com.flowerbowl.web.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/checkId")
    public ResponseEntity<? super IdCheckResponseDto> idCheck(@RequestBody @Valid IdCheckRequestDto requestBody) {
        return authService.idCheck(requestBody);
    }

    @PostMapping("/checkNickname")
    public ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck(@RequestBody @Valid NicknameCheckRequestDto requestBody) {
        return authService.nickNameCheck(requestBody);
    }


    @PostMapping("/sendEmail")
    public ResponseEntity<? super EmailCertificationSendResponseDto> emailCertificationSend(@RequestBody @Valid EmailCertificationSendRequestDto requestBody) {
        return authService.emailCertificationSend(requestBody);
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(@RequestBody @Valid CheckCertificationRequestDto requestBody) {
        return authService.checkCertification(requestBody);
    }

    @PostMapping("/signUp")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        return authService.signUp(requestBody);
    }

    @PostMapping("/signIn")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        return authService.signIn(requestBody);
    }

}
