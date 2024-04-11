package com.flowerbowl.web.dto.request.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpCommunityReqDto {

    private String communityTitle;

    private String communityContent;

    private List<String> communityFileOname;

    private List<String> communityFileSname;

}
