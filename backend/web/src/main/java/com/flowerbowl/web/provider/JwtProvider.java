package com.flowerbowl.web.provider;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.time.Instant;

import com.flowerbowl.web.common.JwtError;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtProvider {


    @Value("${secret-key}")
    private String secretKey;



    public String create(String userId) {

        Date expiredData = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // 만로 시간은 1시간
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(secretKey);

        /*
         * jwt 토큰에는 유저 아이디만 넣었음
         */
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(userId)
//                .claim("roles", roles) // 사용자 권한 역할
//                .claim("userName", user.getUserName()) // 사용자 이름
                .setIssuedAt(new Date())
                .setExpiration(expiredData)
                .compact();

    }

    /**
     * JWT를 검증
     */
    public String validate(String jwt, HttpServletRequest request) {

        String subject = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

        } catch (SignatureException | SecurityException | MalformedJwtException | IllegalArgumentException exception) {
            request.setAttribute("exception", JwtError.INVALID_TOKEN.getCode());
            log.error("Jwt Exception [Err_Msg]: {}", exception.getMessage());
            log.error("class={}", exception.getClass());
            return null;
        } catch (ExpiredJwtException exception) {
            request.setAttribute("exception", JwtError.EXPIRED_TOKEN.getCode());
            log.error("Jwt Exception [Err_Msg]: {}", exception.getMessage());
            log.error("class={}", exception.getClass());
            return null;
        } catch (UnsupportedJwtException exception) {
            request.setAttribute("exception", JwtError.NOT_SUPPORT_TOKEN.getCode());
            log.error("Jwt Exception [Err_Msg]: {}", exception.getMessage());
            log.error("class={}", exception.getClass());
            return null;
        } catch (Exception exception) {
            request.setAttribute("exception", JwtError.NOT_EXIST_TOKEN.getCode());
            log.error("Jwt Exception [Err_Msg]: {}", exception.getMessage());
            log.error("class={}", exception.getClass());
            return null;
        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//            return null;
//        }
        return subject;
    }
}
