package com.flowerbowl.web.dto.response.banner;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
