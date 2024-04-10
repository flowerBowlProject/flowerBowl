package com.flowerbowl.web.dto.recipe.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpRecipeLikeSuResDto extends RecipeResponseDto {

    private Long recipeLikeNo;

    public UpRecipeLikeSuResDto(String code, String message, Long recipeLikeNo) {
        super(code, message);
        this.recipeLikeNo = recipeLikeNo;
    }

}
