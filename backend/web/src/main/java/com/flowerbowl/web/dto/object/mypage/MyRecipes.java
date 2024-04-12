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
    private Long bookmark_cnt;
    private Long comment_cnt;

}
