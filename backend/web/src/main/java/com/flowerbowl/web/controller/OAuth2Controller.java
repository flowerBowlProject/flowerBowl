package com.flowerbowl.web.controller;

import com.flowerbowl.web.handler.OAuth2SuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @PostMapping("/success")
    public ResponseEntity<Map<String, String>> oauth2Success(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        return ResponseEntity.ok().build();
    }
}
