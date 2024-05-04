package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByLessonNoIn(List<Long> lessonNos);

    Lesson findByLessonNo(Long lessonNo);

    Boolean existsLessonByLessonNo(Long lesson_no);

    @Modifying
    @Transactional
    @Query(value = "UPDATE lesson " +
            "SET " +
            "   lesson_writer = :newNickname " +
            "WHERE " +
            "   user_no = (SELECT user_no FROM user WHERE user_id = :userId) ", nativeQuery = true)
    void updateLessonWriter(@Param("userId") String userId, @Param("newNickname") String newNickname);
}
