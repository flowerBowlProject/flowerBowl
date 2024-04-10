package com.flowerbowl.web.dto.recipe.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpRecipeLikeFaResDto extends RecipeResponseDto {

    public UpRecipeLikeFaResDto(String code, String message) {
        super(code, message);
    }

}
