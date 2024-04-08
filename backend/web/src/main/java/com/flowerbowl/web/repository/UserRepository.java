package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNo(Long userNo);

}
