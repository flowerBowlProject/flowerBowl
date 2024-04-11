package com.flowerbowl.web.dto.object.mypage;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class LikeLessons {

    private Long lesson_no;
    private String lesson_title;
    private String lesson_sname;
    private String lesson_oname;

}
