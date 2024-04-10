package com.flowerbowl.web.admin.adminRepository;

import com.flowerbowl.web.domain.Banner;

public interface AdminBannerRepository {
    // 배너 등록
    void bannerRegister(Banner banner);
    // 배너 조회
    Banner findOne();
}
