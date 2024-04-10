package com.flowerbowl.web.lesson.lessonDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllRequestDto {
    private Long page;
    private Long size;
}
