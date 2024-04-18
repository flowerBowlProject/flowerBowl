package com.flowerbowl.web.dto.object.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class LikeRecipes {

    private Long recipe_no;
    private String recipe_title;
    private String recipe_sname;
    private String recipe_oname;
    private String recipe_date;
    private Long recipe_like_cnt;
    private Long comment_cnt;
}
