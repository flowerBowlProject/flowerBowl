package com.flowerbowl.web.repository.admin;

import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserJpaDataRepository extends JpaRepository<User, Long> {
    User findUserByUserNo(Long user_no);
}
