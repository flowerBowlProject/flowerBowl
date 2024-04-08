package com.flowerbowl.web.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class LikeRecipeList {

    private Long recipe_no;
    private String recipe_title;
    private String recipe_sname;
    private String recipe_oname;
}
