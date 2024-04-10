package com.flowerbowl.web.dto.recipe.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRecipeFaResDto extends RecipeResponseDto {

    public GetRecipeFaResDto(String code, String message) {
        super(code, message);
    }

}
