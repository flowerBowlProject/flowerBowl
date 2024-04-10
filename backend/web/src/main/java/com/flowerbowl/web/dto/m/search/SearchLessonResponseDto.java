package com.flowerbowl.web.dto.m.search;

import com.flowerbowl.web.dto.m.ResponseDto;
import com.flowerbowl.web.dto.m.lesson.LessonShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SearchLessonResponseDto extends ResponseDto {
    private String code;
    private String message;
    private List<LessonShortDto> lessonShortDtos = new ArrayList<>();
}