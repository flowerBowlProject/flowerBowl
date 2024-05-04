package com.flowerbowl.web.dto.response.recipe;

import com.flowerbowl.web.dto.object.recipe.GetRecipesPopularDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetRecipesPopularSuResDto extends RecipeResponseDto {

    private List<GetRecipesPopularDto> posts;

    public GetRecipesPopularSuResDto(String code, String message, List<GetRecipesPopularDto> posts) {
        super(code, message);
        this.posts = posts;
    }

}
