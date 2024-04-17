package com.flowerbowl.web.dto.request.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flowerbowl.web.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateRequestDto {
    private String lesson_title;
    private String lesson_price;
    private String lesson_sname;
    private String lesson_oname;
    private String lesson_addr;
    private Double lesson_longitude; // 경도
    private Double lesson_latitude; // 위도
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate lesson_start;
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate lesson_end;
    private Category lesson_category;
    private String lesson_URL;
    // form data저장
    private String lesson_content;
}
