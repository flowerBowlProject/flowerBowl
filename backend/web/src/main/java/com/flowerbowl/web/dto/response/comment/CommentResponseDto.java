package com.flowerbowl.web.dto.response.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private String code;

    private String message;

    public CommentResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
