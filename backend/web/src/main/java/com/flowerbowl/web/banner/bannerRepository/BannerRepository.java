package com.flowerbowl.web.banner.bannerRepository;

import com.flowerbowl.web.domain.Banner;

public interface BannerRepository {
    // 배너 등록
    void bannerRegister(Banner banner);
    // 배너 조회
    Banner findOne();
}
