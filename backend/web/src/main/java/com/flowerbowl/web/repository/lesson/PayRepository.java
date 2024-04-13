package com.flowerbowl.web.repository.lesson;

import com.flowerbowl.web.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long>{
    Long countAllByPayNoGreaterThan(Long no);
}
