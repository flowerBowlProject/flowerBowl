package com.flowerbowl.web.dto.response.community;

import com.flowerbowl.web.dto.object.community.CommunityPageInfo;
import com.flowerbowl.web.dto.object.community.GetAllCommunitiesDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetAllCommunitiesSuResDto extends CommunityResponseDto {

    private List<GetAllCommunitiesDto> posts;

    private CommunityPageInfo pageInfo;

    public GetAllCommunitiesSuResDto(String code, String message, List<GetAllCommunitiesDto> posts, CommunityPageInfo pageInfo) {
        super(code, message);
        this.posts = posts;
        this.pageInfo = pageInfo;
    }

}
