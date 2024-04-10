package com.flowerbowl.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {

    private String code;

    private String message;

    public ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
