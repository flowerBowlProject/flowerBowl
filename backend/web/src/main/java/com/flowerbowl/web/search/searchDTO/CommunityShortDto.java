package com.flowerbowl.web.search.searchDTO;

import com.flowerbowl.web.domain.Community;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CommunityShortDto {
    private Long community_no;
    private String community_title;
    private String community_writer;
    private LocalDate community_date;
    private Long community_views;

    public static CommunityShortDto from(Community community){
        return CommunityShortDto.builder()
                .community_no(community.getCommunityNo())
                .community_title(community.getCommunityTitle())
                .community_writer(community.getCommunityWriter())
                .community_date(community.getCommunityDate())
                .community_views(community.getCommunityViews())
                .build();
    }
}
