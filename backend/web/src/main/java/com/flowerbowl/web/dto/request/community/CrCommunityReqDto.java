package com.flowerbowl.web.dto.request.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CrCommunityReqDto {

    private String community_title;

    private String community_content;

    private List<String> community_file_oname;

    private List<String> community_file_sname;

}
