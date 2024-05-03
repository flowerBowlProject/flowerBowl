package com.flowerbowl.web.dto.response.recipe;

import com.flowerbowl.web.dto.object.recipe.GetRecipesAdminDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetRecipesAdminSuResDto extends RecipeResponseDto {

    private List<GetRecipesAdminDto> posts;

    public GetRecipesAdminSuResDto(String code, String message, List<GetRecipesAdminDto> posts) {
        super(code, message);
        this.posts = posts;
    }

}
