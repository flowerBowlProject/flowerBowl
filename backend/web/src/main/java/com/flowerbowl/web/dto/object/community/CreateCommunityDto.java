package com.flowerbowl.web.dto.object.community;

import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class CreateCommunityDto {

    private String communityTitle;

    private String communityContent;

    private LocalDate communityDate;

    private String communityWriter;

    private Long communityViews;

    private User user;

    public Community  toEntity() {
        return Community.builder()
                .communityTitle(communityTitle)
                .communityContent(communityContent)
                .communityDate(communityDate)
                .communityWriter(communityWriter)
                .communityViews(communityViews)
                .user(user)
                .build();
    }

}
