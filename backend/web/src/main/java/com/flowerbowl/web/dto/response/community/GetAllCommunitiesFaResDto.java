package com.flowerbowl.web.dto.response.community;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAllCommunitiesFaResDto extends CommunityResponseDto {

    public GetAllCommunitiesFaResDto(String code, String message) {
        super(code, message);
    }

}
