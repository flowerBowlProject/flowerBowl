package com.flowerbowl.web.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CrCommentReqDto {

    private Long type;

    private Long post_no;

    private Long parent_no;

    private String comment_content;

}
