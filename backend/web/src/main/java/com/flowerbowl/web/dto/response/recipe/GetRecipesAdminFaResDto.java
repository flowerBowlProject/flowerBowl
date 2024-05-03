package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRecipesAdminFaResDto extends RecipeResponseDto {

    public GetRecipesAdminFaResDto(String code, String message) {
        super(code, message);
    }

}
