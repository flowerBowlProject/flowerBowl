package com.flowerbowl.web.dto.response.lesson;

import com.flowerbowl.web.dto.object.lesson.LessonShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
//@Setter
@AllArgsConstructor
public class FindAllResponseDto extends ResponseDto {
    private String code;
    private String message;
//    List<LessonShortDto> lessons = new ArrayList<>();
    List<LessonShortDto> lessons = new ArrayList<>();
}
