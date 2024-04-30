package com.flowerbowl.web.dto.response.community;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetCommunityFaResDto extends CommunityResponseDto {

    public GetCommunityFaResDto(String code, String message) {
        super(code, message);
    }

}
