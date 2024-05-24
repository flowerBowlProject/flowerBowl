package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Category;
import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByRecipeNo(Long recipeNo);

    List<Recipe> findAllByRecipeNoIn(List<Long> recipeNos);

    List<Recipe> findAllByRecipeCategory(Category recipeCategory);

}
