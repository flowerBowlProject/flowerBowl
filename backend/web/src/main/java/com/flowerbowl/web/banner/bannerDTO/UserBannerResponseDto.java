package com.flowerbowl.web.banner.bannerDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBannerResponseDto {
    private Long code;
    private String message;
    private String banner_sname;
    private String banner_content;
}
