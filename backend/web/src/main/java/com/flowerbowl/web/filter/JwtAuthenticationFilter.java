package com.flowerbowl.web.filter;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.Role;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.provider.JwtProvider;
import com.flowerbowl.web.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    /**
     * bearer Token
     * 필터에서 접근 주체에 정보를 컨트롤러에 넘겨줘야 된다. 그런데 바로 넘길 수 없어서 context를 만들어서 넘겨준다
     * 토큰 관련 context를 만들고 context에 토큰을 만들고 해당 context를 유저 정보 관련 context에 넘긴다
     * 그래서 유저 정보에 대한 context를 만들어서 해당 context에 접근 주체 정보를 담아준다 그리고 해당 context를 컨트롤러에서 사용할 수 있게끔 만든다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = parserBearerToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String userId = jwtProvider.validate(token, request);
            if (userId == null) {
                filterChain.doFilter(request, response);
                return;
            }

            User user = userRepository.findByUserId(userId);
            if (user == null) {
                filterChain.doFilter(request, response);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json; charset=UTF-8");

                JSONObject responseJson = new JSONObject();
                responseJson.put("code", ResponseCode.NOT_EXIST_USER);
                responseJson.put("message", ResponseMessage.NOT_EXIST_USER);

                response.getWriter().print(responseJson);
                return;
            }
            Role role = user.getUserRole(); // role: ROLE_ADMIN, ROLE_USER, ROLE_CHEF

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.getRole()));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

            // 토큰으로부터 인증 객체 생성
            AbstractAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


            // securityContext에 인증된 사용자 정보를 보낸다
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        filterChain.doFilter(request, response);

    }

    /**
     * request로 부터 jwt 토큰 값이 정상인지 확인 후 가져오는 메소드
     */
    private String parserBearerToken(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization); // null, 길이가 0, 공백으로 이루어 있는 지 검사
        if (!hasAuthorization) return null;


        boolean isBearer = authorization.startsWith("Bearer "); // authorization이 bearer 인증 방식 인지 학인
        if (!isBearer) return null;


        return authorization.substring(7);
    }
}
