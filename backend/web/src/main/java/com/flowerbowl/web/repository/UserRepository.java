package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNo(Long userNo);

}
