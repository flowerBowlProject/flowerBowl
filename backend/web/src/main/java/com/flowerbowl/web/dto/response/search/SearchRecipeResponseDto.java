package com.flowerbowl.web.dto.response.search;

import com.flowerbowl.web.dto.object.search.RecipeShortDto;
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
