package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentNo(Long commentNo);

    List<Comment> findAllByParentNo(Long parentNo);

    List<Comment> findAllByRecipe_RecipeNo(Long recipeNo);

    Long countAllByRecipe_RecipeNo(Long recipeNo);
}
