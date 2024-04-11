package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAllRecipesFaResDto extends RecipeResponseDto {

    public GetAllRecipesFaResDto(String code, String message) {
        super(code, message);
    }

}
