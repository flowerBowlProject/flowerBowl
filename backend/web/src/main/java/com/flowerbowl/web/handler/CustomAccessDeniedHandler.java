package com.flowerbowl.web.handler;

import com.flowerbowl.web.common.JwtError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        try {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json; charset=UTF-8");

            JSONObject responseJson = new JSONObject();
            responseJson.put("code", JwtError.NOT_PERMISSION.getCode());
            responseJson.put("message", JwtError.NOT_PERMISSION.getMessage());

            response.getWriter().print(responseJson);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}