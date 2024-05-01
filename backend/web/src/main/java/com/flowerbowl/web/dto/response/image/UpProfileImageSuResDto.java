package com.flowerbowl.web.dto.response.image;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpProfileImageSuResDto extends ImageResponseDto {

    private String profile_oname;

    private String profile_sname;

    public UpProfileImageSuResDto(String code, String message, String profile_oname, String profile_sname) {
        super(code, message);
        this.profile_oname = profile_oname;
        this.profile_sname = profile_sname;
    }

}
