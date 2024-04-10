package com.flowerbowl.web.repository.m;

import com.flowerbowl.web.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuziPayRepository extends JpaRepository<Pay, Long>{
    Long countAllByPayNoGreaterThan(Long no);
}
