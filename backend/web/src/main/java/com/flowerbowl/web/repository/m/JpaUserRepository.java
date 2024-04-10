package com.flowerbowl.web.repository.m;

import com.flowerbowl.web.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository {
    private final EntityManager em;
    public User findByUserNo(Long user_no){
        return em.createQuery("select u from User u where u.userNo = :user_no", User.class)
                .setParameter("user_no", user_no)
                .getSingleResult();
    }
}
