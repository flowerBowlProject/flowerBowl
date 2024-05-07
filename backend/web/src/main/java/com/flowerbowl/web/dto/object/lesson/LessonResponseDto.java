package com.flowerbowl.web.dto.object.lesson;

import com.flowerbowl.web.domain.Category;
import com.flowerbowl.web.domain.Lesson;
import com.flowerbowl.web.domain.LessonFile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class LessonResponseDto {
    private String lesson_title;
    private String lesson_price;
    private LocalDate lesson_date;
    private LocalDate lesson_start;
    private LocalDate lesson_end;
    private String lesson_sname;
    private String lesson_oname;
    private Long lesson_like_cnt; // 이건 query로 넣어줘야 함
    private String lesson_writer;
    private String lesson_URL;
    private String lesson_addr;
    private Double lesson_longitude; // 경도
    private Double lesson_latitude; // 위도
    private Category lesson_category;
    private Boolean lesson_like_status; // 이것도 query로 해결
    private String lesson_contents;
    private List<String> lesson_file_oname = new ArrayList<>();
    private List<String> lesson_file_sname = new ArrayList<>();

    public LessonResponseDto(){}
    public LessonResponseDto(Lesson lesson, List<LessonFile> lessonFileList){
        lesson_title = lesson.getLessonTitle();
        lesson_price = lesson.getLessonPrice();
        lesson_date = lesson.getLessonDate();
        lesson_start = lesson.getLessonStart();
        lesson_end = lesson.getLessonEnd();
        lesson_sname = lesson.getLessonSname();
        lesson_oname = lesson.getLessonOname();
        lesson_writer = lesson.getLessonWriter();
        lesson_addr = lesson.getLessonAddr();
        lesson_longitude = lesson.getLessonLongitude();
        lesson_latitude = lesson.getLessonLatitude();
        lesson_category = lesson.getLessonCategory();
        lesson_contents = lesson.getLessonContent();
        lesson_URL = lesson.getLessonURL();

        for(LessonFile iter : lessonFileList){
            log.info("iter : {} {} {}", iter.getLesson(), iter.getLessonFileOname(), iter.getLessonFileSname());
            lesson_file_oname.add(iter.getLessonFileOname());
            lesson_file_sname.add(iter.getLessonFileSname());
        }
    }
}
