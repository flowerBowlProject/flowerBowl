package com.flowerbowl.web.dto.recipe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
