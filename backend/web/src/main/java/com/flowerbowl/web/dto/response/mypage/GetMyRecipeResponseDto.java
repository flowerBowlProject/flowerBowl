package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.dto.object.MyRecipes;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetMyRecipeResponseDto extends ResponseDto {

    private List<MyRecipes> myRecipes;

    public GetMyRecipeResponseDto(List<MyRecipes> myRecipes) {
        super();
        this.myRecipes = myRecipes;
    }

    public static ResponseEntity<? super GetMyRecipeResponseDto> success(List<MyRecipes> myRecipes){
        GetMyRecipeResponseDto body = new GetMyRecipeResponseDto(myRecipes);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
