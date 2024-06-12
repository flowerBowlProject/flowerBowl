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

    private Long community_no;

    private String community_title;

    private String community_writer;

    private LocalDate community_date;

    private String community_content;

    private List<String> community_file_oname;

    private List<String> community_file_sname;

}
