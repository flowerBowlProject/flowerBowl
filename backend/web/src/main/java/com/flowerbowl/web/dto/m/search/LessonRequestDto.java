package com.flowerbowl.web.dto.m.search;

import com.flowerbowl.web.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LessonRequestDto {
    private String lesson_title;
    private String lesson_price;
    private String lesson_sname;
    private String lesson_oname;
    private String lesson_addr;
    private Double lesson_longitude; // 경도
    private Double lesson_latitude; // 위도
    private LocalDateTime lesson_start;
    private LocalDateTime lesson_end;
    private Category lesson_category;
    private String lesson_URL;
    // form data저장
    private String lesson_content;
}
