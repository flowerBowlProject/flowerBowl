//package com.flowerbowl.web.dto.lesson;
//
//import com.flowerbowl.web.domain.Lesson;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDate;
//
//@Getter
//@Setter
//@Builder
//public class LessonShortGuestDto {
//    private String lesson_title;
//    private LocalDate lesson_date;
//    private String lesson_oname;
//    private String lesson_sname;
//    private Long lesson_likes_num;
//    //    private Long lesson_comments_num;
//    private String lesson_writer;
//    private Boolean lesson_likes_status;
//
//    public static LessonShortGuestDto from(Lesson lesson){
//        return LessonShortGuestDto.builder()
//                .lesson_title(lesson.getLessonTitle())
//                .lesson_date(lesson.getLessonDate())
//                .lesson_oname(lesson.getLessonOname())
//                .lesson_sname(lesson.getLessonSname())
//                .lesson_writer(lesson.getLessonWriter())
//                .lesson_likes_status(false) // 비로그인이므로 false
//                .build();
//    }
//}
