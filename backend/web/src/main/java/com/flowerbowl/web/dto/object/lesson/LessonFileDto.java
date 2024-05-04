package com.flowerbowl.web.dto.object.lesson;

import com.flowerbowl.web.domain.Lesson;
import com.flowerbowl.web.domain.LessonFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LessonFileDto {
    private String lessonFileOname;
    private String lessonFileSname;
    private Lesson lesson;

    public LessonFile toLessonFile(){
        return LessonFile.builder()
                .lessonFileOname(lessonFileOname)
                .lessonFileSname(lessonFileSname)
                .lesson(lesson)
                .build();
    }
}
