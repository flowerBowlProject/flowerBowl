package com.flowerbowl.web.dto.recipe.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpRecipeSuResDto extends ResponseDto {

    private Long recipeNo;

    public UpRecipeSuResDto(String code, String message, Long recipeNo) {
        super(code, message);
        this.recipeNo = recipeNo;
    }

}
