package com.flowerbowl.web.dto.recipe.response;

import com.flowerbowl.web.dto.recipe.GetRecipeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRecipeSuResDto extends ResponseDto {

    private GetRecipeDto data;

    public GetRecipeSuResDto(String code, String message,GetRecipeDto data) {
        super(code, message);
        this.data = data;

    }

}
