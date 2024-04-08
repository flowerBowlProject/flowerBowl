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
    public ResponseEntity<? super IdCheckResponseDTO> idCheck(@RequestBody @Valid IdCheckRequestDTO requestBody) {
        return authService.idCheck(requestBody);
    }

    @PostMapping("/checkNickname")
    public ResponseEntity<? super NicknameCheckResponseDTO> nicknameCheck(@RequestBody @Valid NicknameCheckRequestDTO requestBody) {
        return authService.nickNameCheck(requestBody);
    }


    @PostMapping("/sendEmail")
    public ResponseEntity<? super EmailCertificationSendResponseDTO> emailCertificationSend(@RequestBody @Valid EmailCertificationSendRequestDTO requestBody) {
        return authService.emailCertificationSend(requestBody);
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<? super CheckCertificationResponseDTO> checkCertification(@RequestBody @Valid CheckCertificationRequestDTO requestBody) {
        return authService.checkCertification(requestBody);
    }

    @PostMapping("/signUp")
    public ResponseEntity<? super SignUpResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO requestBody) {
        return authService.signUp(requestBody);
    }

    @PostMapping("/signIn")
    public ResponseEntity<? super SignInResponseDTO> signIn(@RequestBody @Valid SignInRequestDTO requestBody) {
        return authService.signIn(requestBody);
    }

}
