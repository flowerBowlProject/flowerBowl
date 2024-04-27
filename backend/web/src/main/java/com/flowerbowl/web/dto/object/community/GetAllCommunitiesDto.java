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

    private Long community_no;

    private String community_title;

    private String community_writer;

    private LocalDate community_date;

    private Long community_views;

}
