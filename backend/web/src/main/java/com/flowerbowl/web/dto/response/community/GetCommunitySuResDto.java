package com.flowerbowl.web.dto.response.community;

import com.flowerbowl.web.dto.object.community.GetCommunityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetCommunitySuResDto extends CommunityResponseDto {

    private GetCommunityDto data;

    public GetCommunitySuResDto(String code, String message, GetCommunityDto data) {
        super(code, message);
        this.data = data;
    }

}
