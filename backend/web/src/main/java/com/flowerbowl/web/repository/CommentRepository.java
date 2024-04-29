package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentNo(Long commentNo);

    List<Comment> findAllByParentNo(Long parentNo);

    List<Comment> findAllByRecipe_RecipeNo(Long recipeNo);

    Long countAllByRecipe_RecipeNo(Long recipeNo);

    @Query(value = "WITH RECURSIVE CTE AS (" +
            "   SELECT comment_no, comment_content, comment_date, parent_no, community_no, recipe_no, user_no, convert(comment_no, char) as path " +
            "   FROM comment " +
            "   WHERE parent_no IS NULL " +
            "   AND recipe_no = :recipeNo " +
            "   UNION ALL " +
            "   SELECT c.comment_no, c.comment_content, c.comment_date, c.parent_no, c.community_no, c.recipe_no, c.user_no, concat(CTE.comment_no, '-', c.comment_no) AS path " +
            "   FROM comment c " +
            "   INNER JOIN CTE ON c.parent_no = CTE.comment_no " +
            "   WHERE c.recipe_no = :recipeNo" +
            ")" +
            "SELECT comment_no, comment_content, comment_date, parent_no, community_no, recipe_no, user_no, path " +
            "FROM CTE " +
            "ORDER BY CONVERT(SUBSTRING_INDEX(path, '-', 1), UNSIGNED) DESC, parent_no ASC, CONVERT(SUBSTRING_INDEX(path, '-', -1), UNSIGNED) DESC;", nativeQuery = true)
    List<Comment> findCommentsByRecipeNo(@Param("recipeNo") Long recipeNo);

    @Query(value = "WITH RECURSIVE CTE AS (" +
            "   SELECT comment_no, comment_content, comment_date, parent_no, community_no, recipe_no, user_no, convert(comment_no, char) as path " +
            "   FROM comment " +
            "   WHERE parent_no IS NULL " +
            "   AND community_no = :communityNo " +
            "   UNION ALL " +
            "   SELECT c.comment_no, c.comment_content, c.comment_date, c.parent_no, c.community_no, c.recipe_no, c.user_no, concat(CTE.comment_no, '-', c.comment_no) AS path " +
            "   FROM comment c " +
            "   INNER JOIN CTE ON c.parent_no = CTE.comment_no " +
            "   WHERE c.community_no = :communityNo" +
            ")" +
            "SELECT comment_no, comment_content, comment_date, parent_no, community_no, recipe_no, user_no, path " +
            "FROM CTE " +
            "ORDER BY CONVERT(SUBSTRING_INDEX(path, '-', 1), UNSIGNED) DESC, parent_no ASC, CONVERT(SUBSTRING_INDEX(path, '-', 2), UNSIGNED) DESC;", nativeQuery = true)
    List<Comment> findCommentsByCommunityNo(@Param("communityNo") Long communityNo);

}
