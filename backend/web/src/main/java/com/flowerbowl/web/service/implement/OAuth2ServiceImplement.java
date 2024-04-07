package com.flowerbowl.web.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowerbowl.web.domain.CustomOAuth2User;
import com.flowerbowl.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2ServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request); // OAuth2 정보
        String oauthClientName = request.getClientRegistration().getClientName(); // OAuth2 서비스 (kakao, naver)
        try {
            log.info("oAuth2UserInfo{}", new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
            log.info("oauthClientName{}", request.getClientRegistration().getClientName());

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        String userId = null;

        if (oauthClientName.equals("kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            userRepository.insertIfNotExists(userId, "ROLE_USER");
        }

        if (oauthClientName.equals("naver")) {
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + responseMap.get("id");
//            String userEmail = responseMap.get("email");
//            String user_phone = responseMap.get("mobile");
            userRepository.insertIfNotExists(userId, "ROLE_USER");
        }

        return new CustomOAuth2User(userId);



    }


}
