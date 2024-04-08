package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Recipe findByRecipeNo(Long recipeNo);

    void deleteByRecipeNo(Long recipeNo);

}
