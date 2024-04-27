package com.flowerbowl.web.dto.object.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecipesCategoryDto {

    private Long recipe_no;

    private String recipe_sname;

    private String recipe_title;

    private String recipe_writer;

    private LocalDate recipe_date;

    // 레시피 즐겨찾기 수
    private Long recipe_like_count;

    // 레시피 댓글 수
    private Long recipe_comment_count;

    // 사용자 즐겨찾기 여부
    private Boolean recipe_like_status;

}
