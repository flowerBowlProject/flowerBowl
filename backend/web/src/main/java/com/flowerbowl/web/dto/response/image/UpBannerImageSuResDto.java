package com.flowerbowl.web.dto.response.image;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpBannerImageSuResDto extends ImageResponseDto {

    private String banner_oname;

    private String banner_sname;

    public UpBannerImageSuResDto(String code, String message, String banner_oname, String banner_sname) {
        super(code, message);
        this.banner_oname = banner_oname;
        this.banner_sname = banner_sname;
    }

}
