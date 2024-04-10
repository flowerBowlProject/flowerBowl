package com.flowerbowl.web.repository.m;

import com.flowerbowl.web.domain.Pay;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMuziPayRepository{
    private final EntityManager em;
    public JpaMuziPayRepository(EntityManager em){
        this.em = em;
    }
    public void PayCreate(Pay pay){
        em.persist(pay);
    }
}
