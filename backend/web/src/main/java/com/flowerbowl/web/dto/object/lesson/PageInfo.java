package com.flowerbowl.web.dto.object.lesson;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageInfo {
    private int totalPage;
    private Long totalElement;
}
