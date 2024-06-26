package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.RecipeLike;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long>, JpaSpecificationExecutor<RecipeLike> {

    List<RecipeLike> findAllByRecipe_RecipeNo(Long recipeNo);

    List<RecipeLike> findAllByUser_UserNo(Long userNo);

    Long countAllByRecipe_RecipeNo(Long recipeNo);

    Boolean existsRecipeLikeByRecipe_RecipeNoAndUser_UserId(Long recipeNo, String userId);
}
