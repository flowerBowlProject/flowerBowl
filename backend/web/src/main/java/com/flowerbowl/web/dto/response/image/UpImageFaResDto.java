package com.flowerbowl.web.dto.response.image;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpImageFaResDto extends ImageResponseDto {

    public UpImageFaResDto(String code, String message) {
        super(code, message);
    }

}
