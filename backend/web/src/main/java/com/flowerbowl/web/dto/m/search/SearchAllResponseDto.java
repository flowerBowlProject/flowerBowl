package com.flowerbowl.web.dto.m.search;

import com.flowerbowl.web.dto.m.ResponseDto;
import com.flowerbowl.web.dto.m.lesson.LessonShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SearchAllResponseDto extends ResponseDto {
    private String code;
    private String message;
    private List<RecipeShortDto> recipes = new ArrayList<>();
    private List<LessonShortDto> lessons = new ArrayList<>();
    private List<CommunityShortDto> community = new ArrayList<>();
}
