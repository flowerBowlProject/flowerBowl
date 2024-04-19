package com.flowerbowl.web.dto.response.community;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DelCommunityResDto extends CommunityResponseDto {

    public DelCommunityResDto(String code, String message) {
        super(code, message);
    }

}
