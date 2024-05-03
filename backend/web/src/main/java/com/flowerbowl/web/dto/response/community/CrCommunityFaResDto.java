package com.flowerbowl.web.dto.response.community;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrCommunityFaResDto extends CommunityResponseDto {

    public CrCommunityFaResDto(String code, String message) {
        super(code, message);
    }

}
