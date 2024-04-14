package com.flowerbowl.web.dto.object.lesson;

import com.flowerbowl.web.domain.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class LessonShortDto {
    private Long lesson_no;
    private String lesson_title;
    private LocalDate lesson_date;
    private String lesson_oname;
    private String lesson_sname;
    private Long lesson_likes_num;
    private String lesson_writer;
    private Boolean lesson_likes_status;

    public static LessonShortDto from(Lesson lesson){
        return LessonShortDto.builder()
                .lesson_no(lesson.getLessonNo())
                .lesson_title(lesson.getLessonTitle())
                .lesson_date(lesson.getLessonDate())
                .lesson_oname(lesson.getLessonOname())
                .lesson_sname(lesson.getLessonSname())
                .lesson_writer(lesson.getLessonWriter())
                .lesson_likes_status(false) // 이거 수정해야함 // 서비스에서
                .build();
    }
    public static LessonShortDto guestFrom(Lesson lesson){
        return LessonShortDto.builder()
                .lesson_no(lesson.getLessonNo())
                .lesson_title(lesson.getLessonTitle())
                .lesson_date(lesson.getLessonDate())
                .lesson_oname(lesson.getLessonOname())
                .lesson_sname(lesson.getLessonSname())
                .lesson_writer(lesson.getLessonWriter())
                .lesson_likes_status(false) // 비로그인이므로 false
                .build();
    }
}
