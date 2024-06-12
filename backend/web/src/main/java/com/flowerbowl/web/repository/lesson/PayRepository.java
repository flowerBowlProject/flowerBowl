package com.flowerbowl.web.repository.lesson;

import com.flowerbowl.web.domain.Pay;
import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long>{
    Long countAllByPayNoGreaterThan(Long no);

    Pay findByPayNo(Long payNo);
    Boolean existsPayByLesson_LessonNoAndUser(Long lesson_no, User user);

    Pay findPayByPayCodeAndUserUserId(String payCode, String userId);

}
