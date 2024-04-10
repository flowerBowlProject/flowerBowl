package com.flowerbowl.web.lesson.lessonRepository;

import com.flowerbowl.web.domain.Pay;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class PayJpaRepository {
    private final EntityManager em;
    public PayJpaRepository(EntityManager em){
        this.em = em;
    }
    public void PayCreate(Pay pay){
        em.persist(pay);
    }
}
