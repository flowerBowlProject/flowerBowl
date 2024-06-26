package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipeResponseDto {

    private String code;

    private String message;

    public RecipeResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
