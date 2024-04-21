package com.flowerbowl.web.dto.response.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpRecipeLikeSuResDto extends RecipeResponseDto {

    private Long recipe_like_no;

    public UpRecipeLikeSuResDto(String code, String message, Long recipeLikeNo) {
        super(code, message);
        this.recipe_like_no = recipeLikeNo;
    }

}
