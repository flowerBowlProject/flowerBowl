package com.flowerbowl.web.dto.object.lesson;

import com.flowerbowl.web.domain.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LessonMostLikeDto {
    private Long lesson_no;
    private String lesson_sname;
    private String lesson_title;
    private String lesson_content;

    public static LessonMostLikeDto from(Lesson lesson){
        return LessonMostLikeDto.builder()
                .lesson_no(lesson.getLessonNo())
                .lesson_sname(lesson.getLessonSname())
                .lesson_title(lesson.getLessonTitle())
                .lesson_content(lesson.getLessonContent())
                .build();
    }
}
