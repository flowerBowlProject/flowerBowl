package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Category;
import com.flowerbowl.web.domain.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByLessonNoIn(List<Long> lessonNos);

    Lesson findByLessonNo(Long lessonNo);

    Boolean existsLessonByLessonNo(Long lesson_no);

    Page<Lesson> findAllByLessonCategory(Category category, Pageable pageable);

    @Query(value = "select l.*, " +
            "(select count(*) " +
            "from lesson_like ll " +
            "where l.lesson_no = ll.lesson_no) as like_cnt " +
            "from lesson l " +
            "order by like_cnt desc , lesson_no desc "
            , nativeQuery = true)
    Page<Lesson> getMostLiked(Pageable pageable);
}
