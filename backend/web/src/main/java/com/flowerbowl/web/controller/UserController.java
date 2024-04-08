package com.flowerbowl.web.controller;


import com.flowerbowl.web.dto.request.user.FindUserIdRequestDto;
import com.flowerbowl.web.dto.request.user.FindUserPwRequestDto;
import com.flowerbowl.web.dto.request.user.PatchProfileRequestDto;
import com.flowerbowl.web.dto.response.user.*;
import com.flowerbowl.web.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(@AuthenticationPrincipal String userId) {
        return userService.getUserInfo(userId);
    }

    @PatchMapping("/info")
    public ResponseEntity<? super PatchProfileResponseDto> patchUserInfo(
            @AuthenticationPrincipal String userId,
            @RequestBody @Valid PatchProfileRequestDto dto) {
        return userService.patchUserProfile(dto, userId);
    }

    @PatchMapping("/withdrawal")
    public ResponseEntity<? super PatchWdResponseDto> patchWd(
            @AuthenticationPrincipal String userId) {

        return userService.patchWd(userId);
    }

    @PostMapping("/findId")
    public ResponseEntity<? super FindUserIdResponseDto> findId(
            @RequestBody @Valid FindUserIdRequestDto dto) {

        return userService.findId(dto);
    }

    @PostMapping("/findPw")
    public ResponseEntity<? super FindUserPwResponseDto> findPw(
            @RequestBody @Valid FindUserPwRequestDto dto){

        return userService.findPw(dto);
    }
}
