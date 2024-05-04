package com.flowerbowl.web.dto.object.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecipesPopularDto {

    private Long recipe_no;

    private String recipe_sname;

}
