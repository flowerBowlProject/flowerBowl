package com.flowerbowl.web.dto.response.community;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrCommunitySuResDto extends CommunityResponseDto {

    private Long community_no;

    public CrCommunitySuResDto(String code, String message, Long communityNo) {
        super(code, message);
        this.community_no = communityNo;
    }

}
