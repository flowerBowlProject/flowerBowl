package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.RecipeLike;
import org.springframework.data.jpa.domain.Specification;

public class RecipeLikeSpecification {

    public static Specification<RecipeLike> equalRecipeNo(Long recipeNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("recipe").get("recipeNo"), recipeNo);
    }

    public static Specification<RecipeLike> equalUserNo(Long userNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("userNo"), userNo);
    }

}
