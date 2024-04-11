package com.flowerbowl.web.dto.response;


import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ResponseDto {

    private String code;
    private String message;

    public ResponseDto() {
        this.code = ResponseCode.SUCCESS;
        this.message = ResponseMessage.SUCCESS;
    }


    /**
     * 500 서버 오류 및 데이터 베이스 오류
     */
    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto body = new ResponseDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }


    /**
     * 400 유효성 검사 실패
     */
    public static ResponseEntity<ResponseDto> validationError() {
        ResponseDto body = new ResponseDto(ResponseCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


}