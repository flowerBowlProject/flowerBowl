package com.flowerbowl.web.dto.response.user;


import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class FindUserIdResponseDto extends ResponseDto {

    private String user_id;

    public FindUserIdResponseDto(String userId) {
        super();
        this.user_id = userId;
    }


    public static ResponseEntity<? super FindUserIdResponseDto> success(String userId){
        FindUserIdResponseDto body = new FindUserIdResponseDto(userId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> findIdFail(){
        ResponseDto body = new ResponseDto(ResponseCode.FIND_ID_FAIL, ResponseMessage.FIND_ID_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
