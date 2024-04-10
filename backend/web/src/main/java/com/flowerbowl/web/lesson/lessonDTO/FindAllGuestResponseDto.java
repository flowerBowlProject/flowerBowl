package com.flowerbowl.web.lesson.lessonDTO;

import com.flowerbowl.web.commonDTO.LessonShortDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FindAllGuestResponseDto {
    private Long code;
    private String message;
    //    List<>
    List<LessonShortDto> lessons = new ArrayList<>();
}
