package com.flowerbowl.web.dto.response.user;


import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class FindUserPwResponseDto extends ResponseDto {

    public FindUserPwResponseDto() {
        super();
    }

    public static ResponseEntity<? super FindUserPwResponseDto> success(){
        FindUserPwResponseDto body = new FindUserPwResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> informationMismatch(){
        ResponseDto body = new ResponseDto(ResponseCode.FIND_PW_FAIL, ResponseMessage.FIND_PW_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
