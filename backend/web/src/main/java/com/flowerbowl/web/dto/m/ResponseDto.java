package com.flowerbowl.web.dto.m;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
public class ResponseDto {
    private String code;
    private String message;

    public ResponseDto(){
        code = "SU";
        message = "success";
    }
}
