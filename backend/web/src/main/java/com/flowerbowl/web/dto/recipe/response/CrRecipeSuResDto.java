package com.flowerbowl.web.dto.recipe.response;

import lombok.*;

@Getter
@NoArgsConstructor
public class CrRecipeSuResDto extends ResponseDto {

    private Long recipeNo;

    public CrRecipeSuResDto(String code, String message, Long recipeNo) {
        super(code, message);
        this.recipeNo = recipeNo;
    }

}
