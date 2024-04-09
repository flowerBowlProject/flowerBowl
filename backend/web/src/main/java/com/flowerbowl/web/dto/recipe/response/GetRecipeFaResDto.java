package com.flowerbowl.web.dto.recipe.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRecipeFaResDto extends ResponseDto {

    public GetRecipeFaResDto(String code, String message) {
        super(code, message);
    }

}
