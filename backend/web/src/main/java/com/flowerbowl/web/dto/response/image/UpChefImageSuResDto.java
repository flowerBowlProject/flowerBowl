package com.flowerbowl.web.dto.response.image;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpChefImageSuResDto extends ImageResponseDto {

    private String chef_oname;

    private String chef_sname;

    public UpChefImageSuResDto(String code, String message, String chef_oname, String chef_sname) {
        super(code, message);
        this.chef_oname = chef_oname;
        this.chef_sname = chef_sname;
    }

}
