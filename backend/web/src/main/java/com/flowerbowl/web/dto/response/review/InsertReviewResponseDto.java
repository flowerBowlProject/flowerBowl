package com.flowerbowl.web.dto.response.review;

import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class InsertReviewResponseDto extends ResponseDto {

    public InsertReviewResponseDto() {
        super();
    }


    public static ResponseEntity<InsertReviewResponseDto> success() {
        InsertReviewResponseDto body = new InsertReviewResponseDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }


}
