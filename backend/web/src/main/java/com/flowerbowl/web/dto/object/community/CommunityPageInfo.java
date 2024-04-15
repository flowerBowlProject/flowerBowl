package com.flowerbowl.web.dto.object.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityPageInfo {

    private int page;

    private int size;

    private int totalPage;

    private long totalElement;

}
