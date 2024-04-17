package com.flowerbowl.web.repository.search;

import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaDataRecipeRepository extends JpaRepository<Recipe, Long> {
//     Page<Recipe> findAllByRecipeTitleContainingOrRecipeContentContainingOrRecipeStuffContainingOrderByRecipeNoDesc(String query, String keyword2, String keyword3, Pageable pageable);
//     Page<Recipe> findAllByRecipeTitleContainingOrRecipeContentContainingOrderByRecipeNoDesc(String query, String keyword3, Pageable pageable);
     @Query(value = "select * from Recipe r where "
             + "r.recipe_title like %:keyword% or "
             + "r.recipe_content like %:keyword% or "
        + "r.recipe_stuff like concat('%', :keyword , '%')"
             + "order by r.recipe_no desc", nativeQuery = true)
     Page<Recipe> recipeSearch(@Param(value = "keyword") String keyword, Pageable pageable);//
}
