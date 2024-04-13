package com.flowerbowl.web.repository.lesson;

import com.flowerbowl.web.domain.Pay;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PayJpaRepository {
    private final EntityManager em;
    public void PayCreate(Pay pay){
        em.persist(pay);
    }
}
