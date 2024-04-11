package com.flowerbowl.web.dto.response.community;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpCommunityFaResDto extends CommunityResponseDto {

    public UpCommunityFaResDto(String code, String message) {
        super(code, message);
    }

}
