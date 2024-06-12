package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRecipesCategoryFaResDto extends RecipeResponseDto {

    public GetRecipesCategoryFaResDto(String code, String message) {
        super(code, message);
    }

}
