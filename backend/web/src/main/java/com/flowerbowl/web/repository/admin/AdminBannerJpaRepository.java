package com.flowerbowl.web.repository.admin;

import com.flowerbowl.web.domain.Banner;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminBannerJpaRepository implements AdminBannerRepository {
    private final EntityManager em;
    @Override
    public void bannerRegister(Banner banner) {
        // 만약 없으면 생성해주고 있는 경우 교체해 줘야함
        em.persist(banner);
    }
    @Override
    public Banner findOne(){
        return em.createQuery("select b from Banner b", Banner.class)
                .getSingleResult();
    }
}
