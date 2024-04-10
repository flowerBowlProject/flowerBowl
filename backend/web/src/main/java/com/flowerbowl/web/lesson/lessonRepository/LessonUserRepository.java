package com.flowerbowl.web.lesson.lessonRepository;

import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonUserRepository extends JpaRepository<User, Long> {
    User findUserByUserNo(Long user_no);
}
