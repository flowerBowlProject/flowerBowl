package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpRecipeSuResDto extends RecipeResponseDto {

    private Long recipe_no;

    public UpRecipeSuResDto(String code, String message, Long recipeNo) {
        super(code, message);
        this.recipe_no = recipeNo;
    }

}
