package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.RecipeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeFileRepository extends JpaRepository<RecipeFile, Long> {

    RecipeFile findByRecipe_RecipeNo(Long recipeNo);

}
