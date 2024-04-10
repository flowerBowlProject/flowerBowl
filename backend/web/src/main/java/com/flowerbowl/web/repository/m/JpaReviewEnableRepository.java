package com.flowerbowl.web.repository.m;

import com.flowerbowl.web.domain.ReviewEnable;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaReviewEnableRepository {
    private final EntityManager em;
    public void save(ReviewEnable reviewEnable){
        em.persist(reviewEnable);
    }
}
