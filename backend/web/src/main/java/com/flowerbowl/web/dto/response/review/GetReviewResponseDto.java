package com.flowerbowl.web.dto.response.review;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.object.review.GetReviewDto;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetReviewResponseDto extends ResponseDto {

    private Integer review_score;
    private String review_content;
    private String lesson_title;

    public GetReviewResponseDto(GetReviewDto review) {
        super();
        this.review_score = review.getReview_score();
        this.review_content = review.getReview_content();
        this.lesson_title = review.getLesson_title();
    }

    public static ResponseEntity<? super GetReviewResponseDto> success(GetReviewDto review) {
        GetReviewResponseDto body = new GetReviewResponseDto(review);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<? super ResponseDto> noExistReview() {
        ResponseDto body = new ResponseDto(ResponseCode.NOT_EXIST_NUM, ResponseMessage.NOT_EXIST_NUM);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<? super ResponseDto> noMatchUser() {
        ResponseDto body = new ResponseDto(ResponseCode.NOT_MATCH_USER, ResponseMessage.NOT_MATCH_USER);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
}
