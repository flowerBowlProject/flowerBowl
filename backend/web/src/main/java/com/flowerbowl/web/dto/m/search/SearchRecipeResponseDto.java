package com.flowerbowl.web.dto.m.search;

import com.flowerbowl.web.dto.m.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SearchRecipeResponseDto extends ResponseDto {
    private String code;
    private String message;
    private List<RecipeShortDto> recipeShortDtoList = new ArrayList<>();
}
