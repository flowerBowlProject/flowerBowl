package com.flowerbowl.web.dto.community.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityResponseDto {

    private String code;

    private String message;

    public CommunityResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
