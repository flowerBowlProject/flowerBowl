package com.flowerbowl.web.dto.response.recipe;

import com.flowerbowl.web.dto.object.recipe.GetRecipeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRecipeSuResDto extends RecipeResponseDto {

    private GetRecipeDto data;

    public GetRecipeSuResDto(String code, String message,GetRecipeDto data) {
        super(code, message);
        this.data = data;

    }

}
