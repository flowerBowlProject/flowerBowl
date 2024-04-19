package com.flowerbowl.web.dto.response.recipe;

import lombok.*;

@Getter
@NoArgsConstructor
public class CrRecipeSuResDto extends RecipeResponseDto {

    private Long recipeNo;

    public CrRecipeSuResDto(String code, String message, Long recipeNo) {
        super(code, message);
        this.recipeNo = recipeNo;
    }

}
