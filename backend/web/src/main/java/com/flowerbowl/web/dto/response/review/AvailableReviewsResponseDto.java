package com.flowerbowl.web.dto.response.review;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.object.mypage.AvailableReviews;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Getter
public class AvailableReviewsResponseDto extends ResponseDto {

    private List<AvailableReviews> availableReviews;

    public AvailableReviewsResponseDto(List<AvailableReviews> availableReviews) {
        super();
        this.availableReviews = availableReviews;
    }

    public static ResponseEntity<? super AvailableReviewsResponseDto> success(List<AvailableReviews> availableReviews) {
        AvailableReviewsResponseDto body = new AvailableReviewsResponseDto(availableReviews);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<? super ResponseDto> noExistLesson() {
        ResponseDto body = new ResponseDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
