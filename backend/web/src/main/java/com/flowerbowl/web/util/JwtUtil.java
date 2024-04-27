package com.flowerbowl.web.util;

import com.flowerbowl.web.common.JwtError;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.lang.Nullable;

import javax.crypto.SecretKey;
import java.io.IOException;

public class JwtUtil {
    public static void setResponse(HttpServletResponse response, @Nullable JwtError jwtError) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

        JSONObject responseJson = new JSONObject();
        responseJson.put("code", jwtError.getCode());
        responseJson.put("message", jwtError.getMessage());

        response.getWriter().print(responseJson);
    }


    /**
     * 초기에 시크릿 키 만들고 사용은 따로 안함
     *
     * @return secretKey
     */
    public static String jwtSecretKeyMaker() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Encoders.BASE64.encode(key.getEncoded());
    }
}
