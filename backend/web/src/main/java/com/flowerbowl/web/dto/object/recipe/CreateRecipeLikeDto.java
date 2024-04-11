package com.flowerbowl.web.dto.object.recipe;

import com.flowerbowl.web.domain.Recipe;
import com.flowerbowl.web.domain.RecipeLike;
import com.flowerbowl.web.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateRecipeLikeDto {

    private Recipe recipe;

    private User user;

    public RecipeLike toEntity() {
        return RecipeLike.builder()
                .recipe(recipe)
                .user(user)
                .build();
    }

}
