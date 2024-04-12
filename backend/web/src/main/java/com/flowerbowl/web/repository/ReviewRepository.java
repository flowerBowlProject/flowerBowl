package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.LessonRv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<LessonRv, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE lesson_rv " +
            "SET lesson_rv_content = :content, " +
            "    lesson_rv_score = :score, " +
            "    lesson_rv_date = CURRENT_DATE() " +
            "WHERE lesson_rv_no = :no", nativeQuery = true)
    void updateLessonReview(@Param("content") String newLessonRvContent,
                            @Param("score") Integer newLessonRvScore,
                            @Param("no") Long lessonRvNo);


//    LessonRv findByLessonRvNo(Long lessonRvNo);
}
