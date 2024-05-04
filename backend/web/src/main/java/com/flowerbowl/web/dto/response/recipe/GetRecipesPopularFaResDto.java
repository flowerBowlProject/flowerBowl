package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRecipesPopularFaResDto extends RecipeResponseDto {

    public GetRecipesPopularFaResDto(String code, String message) {
        super(code, message);
    }

}
