package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByLessonNoIn(List<Long> lessonNos);

    Lesson findByLessonNo(Long lessonNo);

    Boolean existsLessonByLessonNo(Long lesson_no);
}
