package com.flowerbowl.web.repository;


import com.flowerbowl.web.domain.LessonLike;
import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonLikeRepository extends JpaRepository<LessonLike, Long> {

    List<LessonLike> findAllByUser_UserNo(Long userNo);

    Long countLessonLikeByLesson_LessonNo(Long lessonNo);

    Boolean existsLessonLikeByLesson_LessonNoAndUser_UserId(Long lessonNo, String userId);
}
