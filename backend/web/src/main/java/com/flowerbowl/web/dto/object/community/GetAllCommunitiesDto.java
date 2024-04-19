package com.flowerbowl.web.dto.object.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCommunitiesDto {

    private Long communityNo;

    private String communityTitle;

    private String communityWriter;

    private LocalDate communityDate;

    private Long communityViews;

}
