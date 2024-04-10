package com.flowerbowl.web.banner.bannerDTO;

import com.flowerbowl.web.commonDTO.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BannerResponseDto extends ResponseDto{
    private String code;
    private String message;
    private String banner_sname;
    private String banner_oname;
    private String banner_content;
}
