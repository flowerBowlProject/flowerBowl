package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.ReviewEnable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface ReviewEnableRepository extends JpaRepository<ReviewEnable, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE review_enable " +
            "SET " +
            "   review_enable = false " +
            "WHERE " +
            "   user_no = :userNo AND " +
            "   lesson_no = :lessonNo", nativeQuery = true)
    void updateReviewEnableToFalse(@Param("userNo") Long userNo, @Param("lessonNo") Long lessonNo);
}
