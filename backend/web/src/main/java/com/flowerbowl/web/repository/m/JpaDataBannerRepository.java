package com.flowerbowl.web.repository.m;

import com.flowerbowl.web.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDataBannerRepository extends JpaRepository<Banner, Long> {
    Long countAllByBannerNoGreaterThan(Long base);
//    void
}
