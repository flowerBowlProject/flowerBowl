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

    private Long recipe_no;

    private String recipe_oname;

    private String recipe_sname;

    private List<String> recipe_file_oname;

    private List<String> recipe_file_sname;

    private String recipe_title;

    private String recipe_writer;

    private LocalDate recipe_date;

    private List<String> recipe_stuff;

    private Category recipe_category;

    private String recipe_content;

    // 사용자 즐겨찾기 여부
    private Boolean recipe_like_status;

}
