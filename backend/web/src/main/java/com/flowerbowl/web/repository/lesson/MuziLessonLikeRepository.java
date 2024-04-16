package com.flowerbowl.web.repository.lesson;

import com.flowerbowl.web.domain.LessonLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuziLessonLikeRepository extends JpaRepository<LessonLike, Long> {

    boolean existsByUser_UserNoAndLesson_LessonNo(Long user_userNo, Long lesson_lessonNo);
    Long countLessonLikeByLesson_LessonNo(Long lesson_lessonNo);
    LessonLike findLessonLikeByLesson_LessonNoAndUser_UserNo(Long lesson_no, Long user_no);
}
