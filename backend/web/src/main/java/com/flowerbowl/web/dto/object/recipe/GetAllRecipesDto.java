package com.flowerbowl.web.dto.object.recipe;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRecipesDto {

    private Long recipeNo;

    private String recipeSname;

    private String recipeTitle;

    private String recipeWriter;

    private LocalDate recipeDate;

    // 게시글 즐겨찾기 수
    private Long recipeLikeCount;

    // 게시글 댓글 수
    private Long recipeCommentCount;

    // 사용자 즐겨찾기 여부
    private Boolean recipeLikeStatus;

}
