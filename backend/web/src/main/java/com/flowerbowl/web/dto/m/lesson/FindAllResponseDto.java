package com.flowerbowl.web.dto.m.lesson;

import com.flowerbowl.web.dto.m.ResponseDto;
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
