package com.flowerbowl.web.dto.recipe.response;

import lombok.*;

@Getter
@NoArgsConstructor
public class CrRecipeFaResDto extends ResponseDto{

    public CrRecipeFaResDto(String code, String message) {
        super(code, message);
    }

}
