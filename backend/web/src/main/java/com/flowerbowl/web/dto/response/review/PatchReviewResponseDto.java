package com.flowerbowl.web.dto.response.review;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PatchReviewResponseDto extends ResponseDto {

    public PatchReviewResponseDto() {
        super();
    }

    public static ResponseEntity<? super PatchReviewResponseDto> success(){
        PatchReviewResponseDto body = new PatchReviewResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<?super ResponseDto> notMatchUser() {
        ResponseDto body = new ResponseDto(ResponseCode.DOES_NOT_MATCH, ResponseMessage.DOES_NOT_MATCH);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
}
