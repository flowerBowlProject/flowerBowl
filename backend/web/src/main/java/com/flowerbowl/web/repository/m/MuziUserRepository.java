package com.flowerbowl.web.repository.m;

import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuziUserRepository extends JpaRepository<User, Long> {
    User findUserByUserNo(Long user_no);
}
