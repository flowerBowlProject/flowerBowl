package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpRecipeFaResDto extends RecipeResponseDto {

    public UpRecipeFaResDto(String code, String message) {
        super(code, message);
    }

}
