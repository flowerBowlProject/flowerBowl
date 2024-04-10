package com.flowerbowl.web.dto.recipe.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DelRecipeLikeResDto extends RecipeResponseDto {

    public DelRecipeLikeResDto(String code, String message) {
        super(code, message);
    }

}
