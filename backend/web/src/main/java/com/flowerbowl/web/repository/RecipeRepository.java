package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByRecipeNo(Long recipeNo);

    List<Recipe> findAllByRecipeNoIn(List<Long> recipeNos);
}
