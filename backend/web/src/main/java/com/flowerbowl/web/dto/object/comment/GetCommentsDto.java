package com.flowerbowl.web.dto.object.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsDto {

    private Long comment_no;

    private String comment_writer;

    private String comment_date;

    private String comment_content;

    private String user_file_sname;

    private Long parent_no;

}
