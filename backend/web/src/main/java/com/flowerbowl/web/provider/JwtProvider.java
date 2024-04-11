package com.flowerbowl.web.provider;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.time.Instant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtProvider {


    @Value("${secret-key}")
    private String secretKey;

    /**
     *  초기에 시크릿 키 만들고 사용은 따로 안함
     * @return secretKey
     */
    private String jwtSecretKeyMaker() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Encoders.BASE64.encode(key.getEncoded());
    }

    public String create(String userId) {

        /*
         * jwt 토큰 만료기간은 1시간으로 지정
         */
        Date expiredData = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
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
    public String validate(String jwt) {

        String subject = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return subject;
    }
}
