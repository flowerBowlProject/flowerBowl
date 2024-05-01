package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Lesson;
import com.flowerbowl.web.domain.Pay;
import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long> {
    Boolean existsPayByLesson_LessonNoAndUser(Long lesson_no, User user);
    Long countAllByPayNoGreaterThan(Long no);
}
