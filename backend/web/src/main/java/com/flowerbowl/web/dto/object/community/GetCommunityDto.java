package com.flowerbowl.web.dto.object.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommunityDto {

    private Long communityNo;

    private String communityTitle;

    private String communityWriter;

    private LocalDate communityDate;

    private String communityContent;

    private List<String> communityFileOname;

    private List<String> communityFileSname;

}
