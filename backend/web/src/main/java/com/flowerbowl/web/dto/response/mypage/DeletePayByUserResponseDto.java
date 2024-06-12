package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DeletePayByUserResponseDto extends ResponseDto {

    public DeletePayByUserResponseDto() {
        super();
    }

    public static ResponseEntity<? super DeletePayByUserResponseDto> success(){
        DeletePayByUserResponseDto body = new DeletePayByUserResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }


    public static ResponseEntity<ResponseDto> notExistPayNo(){
        ResponseDto body = new ResponseDto(ResponseCode.NOT_EXIST_NUM, ResponseMessage.NOT_EXIST_NUM);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
//
//    public static ResponseEntity<? super ResponseDto> notMatchUser() {
//        ResponseDto body = new ResponseDto(ResponseCode.DOES_NOT_MATCH, ResponseMessage.DOES_NOT_MATCH);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
//    }
}
