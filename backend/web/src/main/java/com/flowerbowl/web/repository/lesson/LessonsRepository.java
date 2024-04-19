package com.flowerbowl.web.repository.lesson;

import com.flowerbowl.web.domain.Lesson;

import java.util.List;

public interface LessonsRepository {
    // 클래스 등록
    void LessonCreate(Lesson lesson);
    public Lesson findByLesson_no(Long lesson_no);
    public List<Lesson> findAllUser(Long user_no);
}
