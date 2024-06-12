package com.flowerbowl.web.dto.response.image;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponseDto {

    private String code;

    private String message;

    public ImageResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
