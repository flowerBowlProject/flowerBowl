package com.flowerbowl.web.dto.response.community;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpCommunitySuResDto extends CommunityResponseDto {

    private Long community_no;

    public UpCommunitySuResDto(String code, String message, Long communityNo) {
        super(code, message);
        this.community_no = communityNo;
    }

}
