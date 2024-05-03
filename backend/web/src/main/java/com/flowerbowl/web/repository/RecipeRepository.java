package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Category;
import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
    List<Recipe> findREcipesByRoleAdmin();

}
