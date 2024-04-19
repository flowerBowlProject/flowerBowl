package com.flowerbowl.web.dto.object.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PayLessons {

    private String lesson_title;
    private String lesson_writer;
    private String pay_date;
    private Long review_score;


}
