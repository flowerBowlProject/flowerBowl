package com.flowerbowl.web.dto.response.review;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.object.WrittenReviews;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class WrittenReviewsResponseDto extends ResponseDto {

    private List<WrittenReviews> writtenReviews;

    public WrittenReviewsResponseDto(List<WrittenReviews> writtenReviews) {
        super();
        this.writtenReviews = writtenReviews;
    }

    public static ResponseEntity<? super WrittenReviewsResponseDto> success(List<WrittenReviews> writtenReviews){
        WrittenReviewsResponseDto body = new WrittenReviewsResponseDto(writtenReviews);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<? super ResponseDto> noExistReview(){
        ResponseDto body = new ResponseDto(ResponseCode.NOT_EXIST_REVIEW, ResponseMessage.NOT_EXIST_REVIEW);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


}
