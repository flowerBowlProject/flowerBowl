package com.flowerbowl.web.dto.object.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class GetReviewDto {

    private Integer review_score;
    private String review_content;
    private String lesson_title;

}
