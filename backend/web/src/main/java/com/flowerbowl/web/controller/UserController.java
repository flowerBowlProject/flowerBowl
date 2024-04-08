package com.flowerbowl.web.controller;


import com.flowerbowl.web.dto.request.user.PatchProfileRequestDto;
import com.flowerbowl.web.dto.response.user.GetUserInfoResponseDto;
import com.flowerbowl.web.dto.response.user.PatchProfileResponseDto;
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
    public ResponseEntity<? super PatchProfileResponseDto> PatchUserInfo(
            @AuthenticationPrincipal String userId,
            @RequestBody @Valid PatchProfileRequestDto dto) {
        return userService.putUserProfile(dto,userId);
    }

}
