package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByRecipeNoIn(List<Long> recipeNos);
}