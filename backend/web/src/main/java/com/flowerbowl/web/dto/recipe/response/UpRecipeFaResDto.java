package com.flowerbowl.web.dto.recipe.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpRecipeFaResDto extends ResponseDto {

    public UpRecipeFaResDto(String code, String message) {
        super(code, message);
    }

}
