package com.flowerbowl.web.dto.object.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class WrittenReviews {

    private String lesson_title;
    private String lesson_writer;
    private Long review_no;
    private String review_date;
    private Integer review_score;
}
