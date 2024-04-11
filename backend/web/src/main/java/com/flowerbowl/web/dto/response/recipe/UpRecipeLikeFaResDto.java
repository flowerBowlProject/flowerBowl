package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpRecipeLikeFaResDto extends RecipeResponseDto {

    public UpRecipeLikeFaResDto(String code, String message) {
        super(code, message);
    }

}
