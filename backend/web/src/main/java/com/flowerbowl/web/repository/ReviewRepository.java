package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.LessonRv;
import com.flowerbowl.web.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Query(value = "SELECT " +
            "   lv.lesson_rv_score AS review_score, " +
            "   lv.lesson_rv_content AS review_content, " +
            "   l.lesson_title " +
            "FROM " +
            "   lesson_rv lv " +
            "   INNER JOIN lesson l ON l.lesson_no = lv.lesson_no " +
            "WHERE " +
            "   lesson_rv_no = :reviewNo", nativeQuery = true)
    List<Object[]> findReviewByReviewNo(@Param("reviewNo") Long reviewNo);

    LessonRv findByLessonRvNo(Long reviewNo);

//    LessonRv findByLessonRvNo(Long lessonRvNo);

    //
    Page<LessonRv> findLessonRvByLesson_LessonNo(Long lesson_no, Pageable pageable);
}
