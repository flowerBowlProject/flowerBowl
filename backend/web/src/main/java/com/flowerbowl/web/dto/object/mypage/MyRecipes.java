package com.flowerbowl.web.dto.object.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MyRecipes {

    private String recipe_date;
    private String recipe_title;
    private Long recipe_like_cnt;
    private Long comment_cnt;
    private Long recipe_no;

}
