package com.flowerbowl.web.handler;

import com.flowerbowl.web.domain.CustomOAuth2User;
import com.flowerbowl.web.provider.JwtProvider;
import com.flowerbowl.web.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String userId = oAuth2User.getName();
        boolean isMatchNickname = oAuth2User.isMatchNickname();
        String token = jwtProvider.create(userId);

        response.sendRedirect("http://43.201.133.138:8080/auth/oauth-response?token=" +
                token + "&expirationTime=3600&isMatchNickname="+isMatchNickname);
    }
}
