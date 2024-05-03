package com.flowerbowl.web.dto.response.search;

import com.flowerbowl.web.dto.object.search.LessonShortDto;
import com.flowerbowl.web.dto.object.search.CommunityShortDto;
import com.flowerbowl.web.dto.object.search.RecipeShortDto;
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
