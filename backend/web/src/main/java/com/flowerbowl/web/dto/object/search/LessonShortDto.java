package com.flowerbowl.web.dto.object.search;//package com.flowerbowl.web.dto.commonDTO;

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
    private LocalDate lesson_date; // 게시글 작성 날짜
    private String lesson_oname;
    private String lesson_sname;
    private Long lesson_like_cnt; // 즐겨찾기 수
    private String lesson_writer;
    private Boolean lesson_like_status;

    public static LessonShortDto from(Lesson lesson){
        return LessonShortDto.builder()
                .lesson_no(lesson.getLessonNo())
                .lesson_title(lesson.getLessonTitle())
                .lesson_date(lesson.getLessonDate())
                .lesson_oname(lesson.getLessonOname())
                .lesson_sname(lesson.getLessonSname())
                .lesson_writer(lesson.getLessonWriter())
                .lesson_like_status(false) // 이거 수정해야함 // 서비스에서
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
                .lesson_like_status(false) // 비로그인이므로 false
                .build();
    }
}
