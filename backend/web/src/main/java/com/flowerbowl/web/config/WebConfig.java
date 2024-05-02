package com.flowerbowl.web.config;


import com.flowerbowl.web.common.JwtError;
import com.flowerbowl.web.filter.JwtAuthenticationFilter;
import com.flowerbowl.web.handler.CustomAccessDeniedHandler;
import com.flowerbowl.web.handler.OAuth2SuccessHandler;
import com.flowerbowl.web.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

@EnableWebSecurity
@Configuration
@Configurable
@RequiredArgsConstructor
public class WebConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DefaultOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    /**
     * HttpSecurity 설정
     */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(cors -> cors
                        .configurationSource(configurationSource())
                )
                .csrf(CsrfConfigurer::disable) // CSRF 보안 설정을 비 활성화
                .httpBasic(HttpBasicConfigurer::disable) // 스프링 서큐리티 기본 인증 설정을 비 활성화(기본 인식 방식이 basic)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // HTTP 요청에 대한 권한 설정
                .authorizeHttpRequests(request -> request
                                .requestMatchers("/", "/file/**", "/error", // 스프링이 웰컴 페이지를 찾으려고 하는데 못 찾으니까 error페이지로 이동하려고 하는거 같다
                                        "/api/recipes/guest", "/api/recipes/guest/**",
                                        "/api/communities/detail/*",
                                        "/api/guest/**", "/api/banners",
                                        "/api/users/findId", "/api/users/findPw",
                                        "/oauth2/**", "/api/auth/**",
                                        "/api/search", "/api/search/**").permitAll() // 역할을 따른 경로 접근 제한 설정
                                .requestMatchers(RegexRequestMatcher.regexMatcher("\\/api\\/communities\\/list(?=\\?).*")).permitAll()
                                .requestMatchers(RegexRequestMatcher.regexMatcher("\\/api\\/comments(?=\\?).*")).permitAll()
//                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() 정적 자원들은 필요가 없나?
//                        .requestMatchers("/api/user/**").hasAnyRole("USER", "CHEF") // 나머지 요청은 인증된 사용자만 접근이 가능해서 필요가 없는 거 같음
                                .requestMatchers("/api/chef/**",
                                        "/api/lessons/**","/api/lessons").hasRole("CHEF")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated() // 나머지 요청은 인증된 사용자만 접근
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/oauth2"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }

    /*
     * CORS 구성 설정
     */
    @Bean
    protected CorsConfigurationSource configurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대한 CORS 구성 추가

        return source;
    }
}

/**
 * 인증 실패 시 처리할 클래스
 */
@Slf4j
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String exception = (String) request.getAttribute("exception");
        log.info("requestURL={}", request.getRequestURL());
//        log.info("exception Values={}", exception);

        if (exception != null && exception.equals("IT")) {
            JwtUtil.setResponse(response, JwtError.INVALID_TOKEN);
        } else if (exception != null && exception.equals("NS")) {
            JwtUtil.setResponse(response, JwtError.NOT_SUPPORT_TOKEN);
        } else if (exception != null && exception.equals("ET")) {
            JwtUtil.setResponse(response, JwtError.EXPIRED_TOKEN);
        } else if (exception != null && exception.equals("NT")) {
            JwtUtil.setResponse(response, JwtError.NOT_EXIST_TOKEN);
        } else {
            JwtUtil.setResponse(response, JwtError.NOT_FIND_EXCEPTION);
        }

//        response.setContentType("application/json; charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        log.info("requestURL{}", request.getRequestURL());
//        response.getWriter().write("{\"code\": \"NP\", \"message\": \"No Permission\"}");

    }


}