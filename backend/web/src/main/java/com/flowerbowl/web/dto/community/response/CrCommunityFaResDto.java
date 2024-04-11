package com.flowerbowl.web.dto.community.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrCommunityFaResDto extends CommunityResponseDto {

    public CrCommunityFaResDto(String code, String message) {
        super(code, message);
    }

}
