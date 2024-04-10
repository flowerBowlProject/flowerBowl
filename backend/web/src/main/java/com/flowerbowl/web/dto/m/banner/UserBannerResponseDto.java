package com.flowerbowl.web.dto.m.banner;

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
