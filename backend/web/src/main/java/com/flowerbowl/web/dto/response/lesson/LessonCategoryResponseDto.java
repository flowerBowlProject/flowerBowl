package com.flowerbowl.web.dto.response.lesson;

import com.flowerbowl.web.dto.object.lesson.LessonShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LessonCategoryResponseDto extends ResponseDto{
    private String code;
    private String message;

    private List<LessonShortDto> lessons;
}
