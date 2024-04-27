package com.flowerbowl.web.dto.response.recipe;

import lombok.*;

@Getter
@NoArgsConstructor
public class CrRecipeSuResDto extends RecipeResponseDto {

    private Long recipe_no;

    public CrRecipeSuResDto(String code, String message, Long recipeNo) {
        super(code, message);
        this.recipe_no = recipeNo;
    }

}
