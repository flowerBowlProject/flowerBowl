package com.flowerbowl.web.dto.response.recipe;

import com.flowerbowl.web.dto.object.recipe.GetRecipesCategoryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetRecipesCategorySuResDto extends RecipeResponseDto {

    private List<GetRecipesCategoryDto> posts;

    public GetRecipesCategorySuResDto(String code, String message, List<GetRecipesCategoryDto> posts) {
        super(code, message);
        this.posts = posts;
    }

}
