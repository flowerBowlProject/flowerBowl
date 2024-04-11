package com.flowerbowl.web.dto.community.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CrCommunityReqDto {

    private String communityTitle;

    private String communityContent;

    private List<String> communityFileOname;

    private List<String> communityFileSname;

}
