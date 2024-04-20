package com.flowerbowl.web.dto.object.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MyLessons {

    private String lesson_date;
    private String lesson_title;
    private Long bookmark_cnt;
    private Long review_cnt;
    private Long lesson_no;
}
