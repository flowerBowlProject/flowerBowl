package com.flowerbowl.web.dto.m.lesson;

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
