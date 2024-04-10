package com.flowerbowl.web.lesson.lessonDTO;

import com.flowerbowl.web.commonDTO.LessonShortDto;
import com.flowerbowl.web.commonDTO.ResponseDto;
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
