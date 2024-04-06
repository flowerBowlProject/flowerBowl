package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query(value = "INSERT INTO user (user_id) SELECT :userId " +
            "WHERE NOT EXISTS (SELECT 1 FROM user WHERE user_id = :userId)", nativeQuery = true)
    void insertIfNotExists(@Param("userId") String userId);

    User findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserNickname(String userNickname);


}

