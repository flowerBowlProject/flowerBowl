package com.flowerbowl.web.dto.object.search;

import com.flowerbowl.web.domain.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RecipeShortDto {
    private Long recipe_no;
    private String recipe_title;
    private String recipe_sname;
    private String recipe_oname;
    private String recipe_writer;
    private LocalDate recipe_date;
    // 따로 추가해줘야 하는 부분
    private Long recipe_like_cnt; // 좋아요 수
    private Long comment_cnt; // 댓글 수
    private Boolean recipe_like_status; // s 뺌

    public static RecipeShortDto from(Recipe recipe){
        return RecipeShortDto.builder()
                .recipe_no(recipe.getRecipeNo())
                .recipe_title(recipe.getRecipeTitle())
                .recipe_sname(recipe.getRecipeSname())
                .recipe_oname(recipe.getRecipeOname())
                .recipe_writer(recipe.getRecipeWriter())
                .recipe_date(recipe.getRecipeDate())
                .build();
    }
}
