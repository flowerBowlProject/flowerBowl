package com.flowerbowl.web.dto.object.recipe;

import com.flowerbowl.web.domain.Recipe;
import com.flowerbowl.web.domain.RecipeFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreateRecipeFileDto {

    private List<String> recipeFileOname;

    private List<String> recipeFileSname;

    private Recipe recipe;

    public RecipeFile toEntity() {
        return RecipeFile.builder()
                .recipeFileOname(recipeFileOname)
                .recipeFileSname(recipeFileSname)
                .recipe(recipe)
                .build();
    }

}
