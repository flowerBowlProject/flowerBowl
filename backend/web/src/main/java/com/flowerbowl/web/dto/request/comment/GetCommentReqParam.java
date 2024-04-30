package com.flowerbowl.web.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCommentReqParam {

    private Long type;

    private Long post_no;

    private int page;

    private int size;

}
