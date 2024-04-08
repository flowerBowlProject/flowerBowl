package com.flowerbowl.web.dto.recipe.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetAllRecipesSuResDto extends ResponseDto {

    private List<GetAllRecipesDto> posts;

    public GetAllRecipesSuResDto(String code, String message, List<GetAllRecipesDto> posts) {
        super(code, message);
        this.posts = posts;
    }

}
