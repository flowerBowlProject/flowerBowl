package com.flowerbowl.web.dto.object.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentPageInfo {

    private int page;

    private int size;

    private int totalPage;

    private long totalElement;

}
