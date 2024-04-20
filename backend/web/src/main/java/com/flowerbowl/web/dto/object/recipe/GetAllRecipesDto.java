package com.flowerbowl.web.dto.object.recipe;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRecipesDto {

    private Long recipe_no;

    private String recipe_sname;

    private String recipe_title;

    private String recipe_writer;

    private LocalDate recipe_date;

    // 게시글 즐겨찾기 수
    private Long recipe_like_count;

    // 게시글 댓글 수
    private Long recipe_comment_count;

    // 사용자 즐겨찾기 여부
    private Boolean recipe_like_status;

}
