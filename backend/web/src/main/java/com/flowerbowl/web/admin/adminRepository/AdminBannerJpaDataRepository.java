package com.flowerbowl.web.admin.adminRepository;

import com.flowerbowl.web.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminBannerJpaDataRepository extends JpaRepository<Banner, Long> {
    Long countAllByBannerNoGreaterThan(Long base);
//    void
}
