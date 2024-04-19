package com.flowerbowl.web.dto.response.recipe;

import lombok.*;

@Getter
@NoArgsConstructor
public class CrRecipeFaResDto extends RecipeResponseDto {

    public CrRecipeFaResDto(String code, String message) {
        super(code, message);
    }

}
