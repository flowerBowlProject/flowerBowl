package com.flowerbowl.web.dto.request.lesson;

import com.flowerbowl.web.domain.Category;
import com.flowerbowl.web.domain.LessonFile;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    private LocalDate lesson_start;
    private LocalDate lesson_end;
    private Category lesson_category;
    private String lesson_URL;
    private String lesson_content;
    private List<String> lesson_file_oname;
    private List<String> lesson_file_sname;
}
