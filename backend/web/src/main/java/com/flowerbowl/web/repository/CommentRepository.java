package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByRecipe_RecipeNo(Long recipeNo);

    Long countAllByRecipe_RecipeNo(Long recipeNo);
}
