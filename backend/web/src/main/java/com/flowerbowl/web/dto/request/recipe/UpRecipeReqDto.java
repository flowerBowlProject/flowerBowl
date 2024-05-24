package com.flowerbowl.web.dto.request.recipe;

import com.flowerbowl.web.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpRecipeReqDto {

    private String recipe_title;

    private Category recipe_category;

    private List<String> recipe_stuff;

    private String recipe_content;

    private String recipe_oname;

    private String recipe_sname;

    private List<String> recipe_file_oname;

    private List<String> recipe_file_sname;

}
