package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Category;
import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByRecipeNo(Long recipeNo);

    List<Recipe> findAllByRecipeNoIn(List<Long> recipeNos);

    List<Recipe> findAllByRecipeCategory(Category recipeCategory);

    @Query(value = "SELECT r.* " +
            "FROM recipe r " +
            "LEFT JOIN user u " +
            "ON r.user_no = u.user_no " +
            "WHERE user_role = \"ROLE_ADMIN\" " +
            "ORDER BY recipe_no DESC " +
            "LIMIT 5;", nativeQuery = true)
    List<Recipe> findRecipesByRoleAdmin();

    @Query(value = "SELECT r.*, " +
            "(SELECT COUNT(*) " +
            "FROM recipe_like l " +
            "WHERE l.recipe_no = r.recipe_no) AS bookmark_cnt " +
            "FROM recipe r " +
            "ORDER BY bookmark_cnt DESC, recipe_no DESC " +
            "LIMIT 5;", nativeQuery = true)
    List<Recipe> findRecipesByPopularity();

    @Modifying
    @Transactional
    @Query(value = "UPDATE recipe " +
            "SET " +
            "   recipe_writer = :newNickname " +
            "WHERE " +
            "   user_no = (SELECT user_no FROM user WHERE user_id = :userId) ", nativeQuery = true)
    void updateRecipeWriter(@Param("userId") String userId, @Param("newNickname") String newNickname);
}
