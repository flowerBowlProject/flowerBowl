package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.object.LikeRecipeList;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetRecipeLikeListResponseDto extends ResponseDto {

    private List<LikeRecipeList> likeRecipeList;

    public GetRecipeLikeListResponseDto(List<LikeRecipeList> likeRecipeList) {
        super();
        this.likeRecipeList = likeRecipeList;
    }

    public static ResponseEntity<GetRecipeLikeListResponseDto> success(List<LikeRecipeList> likeLessonList) {
        GetRecipeLikeListResponseDto body = new GetRecipeLikeListResponseDto(likeLessonList);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistRecipe() {
        ResponseDto body = new ResponseDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
