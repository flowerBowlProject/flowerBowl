package com.flowerbowl.web.repository.m;

import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JpaDataRecipeRepository extends JpaRepository<Recipe, Long> {
     Page<Recipe> findAllByRecipeTitleContainingOrRecipeContentContainingOrRecipeStuffContainingOrderByRecipeNoDesc(String query, String keyword2, String keyword3, Pageable pageable);
}
