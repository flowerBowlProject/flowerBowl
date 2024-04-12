package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByRecipeNoIn(List<Long> recipeNos);
}
