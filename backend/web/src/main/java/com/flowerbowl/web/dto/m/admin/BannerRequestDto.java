package com.flowerbowl.web.dto.m.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerRequestDto {
    private String admin_banner_sname;
    private String admin_banner_oname;
    private String admin_content;
}
