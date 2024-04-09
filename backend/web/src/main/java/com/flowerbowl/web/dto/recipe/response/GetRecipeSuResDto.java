package com.flowerbowl.web.dto.recipe.response;

import com.flowerbowl.web.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetRecipeSuResDto extends ResponseDto {

    private GetRecipeDto data;

    public GetRecipeSuResDto(String code, String message,GetRecipeDto data) {
        super(code, message);
        this.data = data;

    }

}
