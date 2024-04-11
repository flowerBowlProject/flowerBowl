package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.object.mypage.LikeRecipes;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetRecipeLikesResponseDto extends ResponseDto {

    private List<LikeRecipes> likeRecipes;

    public GetRecipeLikesResponseDto(List<LikeRecipes> likeRecipes) {
        super();
        this.likeRecipes = likeRecipes;
    }

    public static ResponseEntity<GetRecipeLikesResponseDto> success(List<LikeRecipes> likeRecipes) {
        GetRecipeLikesResponseDto body = new GetRecipeLikesResponseDto(likeRecipes);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistRecipe() {
        ResponseDto body = new ResponseDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
