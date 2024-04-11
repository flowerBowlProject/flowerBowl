package com.flowerbowl.web.dto.response.recipe;

import com.flowerbowl.web.dto.object.recipe.GetAllRecipesDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetAllRecipesSuResDto extends RecipeResponseDto {

    private List<GetAllRecipesDto> posts;

    public GetAllRecipesSuResDto(String code, String message, List<GetAllRecipesDto> posts) {
        super(code, message);
        this.posts = posts;
    }

}
