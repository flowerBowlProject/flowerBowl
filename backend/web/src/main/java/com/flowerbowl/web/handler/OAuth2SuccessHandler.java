package com.flowerbowl.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowerbowl.web.domain.CustomOAuth2User;
import com.flowerbowl.web.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String userId = oAuth2User.getName();
        String token = jwtProvider.create(userId);

//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("access_token", token);
//        responseData.put("expirationTime", 3600);
//
//        response.setHeader("Authorization", "Bearer " + token);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(objectMapper.writeValueAsString(responseData));
        response.sendRedirect("http://localhost:3000/auth/oauth-response?token=" + token + "&expirationTime=3600");
    }
}
