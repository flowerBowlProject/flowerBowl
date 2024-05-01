package com.flowerbowl.web.dto.response.image;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpThumbnailImageSuResDto extends ImageResponseDto {

    private String thumbnail_oname;

    private String thumbnail_sname;

    public UpThumbnailImageSuResDto(String code, String message, String thumbnail_oname, String thumbnail_sname) {
        super(code, message);
        this.thumbnail_oname = thumbnail_oname;
        this.thumbnail_sname = thumbnail_sname;
    }

}
