package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DelRecipeResDto extends RecipeResponseDto {

    public DelRecipeResDto(String code, String message) {
        super(code, message);
    }

}
