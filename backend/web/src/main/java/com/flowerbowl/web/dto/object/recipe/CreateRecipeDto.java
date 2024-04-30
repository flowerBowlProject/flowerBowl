package com.flowerbowl.web.dto.object.recipe;

import com.flowerbowl.web.domain.Category;
import com.flowerbowl.web.domain.Recipe;
import com.flowerbowl.web.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreateRecipeDto {

    private String recipeTitle;

    private LocalDate recipeDate;

    private List<String> recipeStuff;

    private String recipeOname;

    private String recipeSname;

    private String recipeContent;

    private Category recipeCategory;

    private String recipeWriter;

    private Long recipeViews;

    private User user;

    public Recipe toEntity() {
        return Recipe.builder()
                .recipeTitle(recipeTitle)
                .recipeDate(recipeDate)
                .recipeStuff(recipeStuff)
                .recipeOname(recipeOname)
                .recipeSname(recipeSname)
                .recipeContent(recipeContent)
                .recipeCategory(recipeCategory)
                .recipeWriter(recipeWriter)
                .recipeViews(recipeViews)
                .user(user)
                .build();
    }

}
