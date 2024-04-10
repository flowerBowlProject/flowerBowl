package com.flowerbowl.web.dto.m.search;

import com.flowerbowl.web.domain.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecipeShortDto {
    private Long recipe_no;
    private String recipe_title;
    private String recipe_sname;
    private String recipe_oname;
    private String recipe_writer;

    public static RecipeShortDto from(Recipe recipe){
        return RecipeShortDto.builder()
                .recipe_no(recipe.getRecipeNo())
                .recipe_title(recipe.getRecipeTitle())
                .recipe_sname(recipe.getRecipeSname())
                .recipe_oname(recipe.getRecipeOname())
                .recipe_writer(recipe.getRecipeWriter())
                .build();
    }
}
