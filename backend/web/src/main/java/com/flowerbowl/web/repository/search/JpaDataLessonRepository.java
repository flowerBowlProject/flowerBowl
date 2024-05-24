package com.flowerbowl.web.repository.search;

import com.flowerbowl.web.domain.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDataLessonRepository extends JpaRepository<Lesson, Long> {
    Boolean existsLessonByLessonNo(Long lesson_no);
    Lesson findLessonByLessonNo(Long lesson_no);
//    Page<Lesson> findAllByLessonNoGreaterThanOOrderByLessonNoDesc(int cmp, Pageable pageable);
    Page<Lesson> findAllByOrderByLessonNoDesc(Pageable pageable);
    Page<Lesson> findAllByLessonTitleContainingOrLessonContentContainingOrderByLessonNo(String keyword1, String keyword2, Pageable pageable);
    Page<Lesson> findAllByLessonTitleContaining(String query2, Pageable pageable);
    Page<Lesson> findAllByLessonTitleContainingOrLessonContentContainingAndLessonDeleteStatusOrderByLessonNo(String keyword1, String keyword2, Boolean deleteStatus, Pageable pageable);
    Page<Lesson> findLessonByLessonDeleteStatusAndLessonTitleContainingOrLessonContentContainingOrderByLessonNoDesc(Boolean deleteStatue, String keyword1, String keyword2, Pageable pageable);
}