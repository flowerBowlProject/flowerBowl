package com.flowerbowl.web.dto.recipe.request;

import com.flowerbowl.web.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpRecipeReqDto {

    private String recipeTitle;

    private Category recipeCategory;

    private List<String> recipeStuff;

    private String recipeContent;

    private String recipeOname;

    private String recipeSname;

    private List<String> recipeFileOname;

    private List<String> recipeFileSname;

}
