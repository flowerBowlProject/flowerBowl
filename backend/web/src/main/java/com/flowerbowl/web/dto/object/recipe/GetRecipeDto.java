package com.flowerbowl.web.dto.object.recipe;

import com.flowerbowl.web.domain.Category;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecipeDto {

    private Long recipeNo;

    private String recipeOname;

    private String recipeSname;

    private List<String> recipeFileOname;

    private List<String> recipeFileSname;

    private String recipeTitle;

    private String recipeWriter;

    private LocalDate recipeDate;

    private List<String> recipeStuff;

    private Category recipeCategory;

    private String recipeContent;

    // 사용자 즐겨찾기 여부
    private Boolean recipeLikeStatus;

}
