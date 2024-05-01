package com.flowerbowl.web.dto.response.image;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpContentImageSuResDto extends ImageResponseDto {

    private String content_oname;

    private String content_sname;

    public UpContentImageSuResDto(String code, String message, String content_oname, String content_sname) {
        super(code, message);
        this.content_oname = content_oname;
        this.content_sname = content_sname;
    }

}
