package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.RecipeLike;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long>, JpaSpecificationExecutor<RecipeLike> {

    List<RecipeLike> findAllByRecipe_RecipeNo(Long recipeNo);

    List<RecipeLike> findAllByUser_UserNo(Long userNo);

}
