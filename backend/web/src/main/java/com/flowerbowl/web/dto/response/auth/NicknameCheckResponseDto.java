package com.flowerbowl.web.dto.response.auth;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class NicknameCheckResponseDto extends ResponseDto {

    private NicknameCheckResponseDto(){
        super();
    }

    public static ResponseEntity<NicknameCheckResponseDto> success(){
        NicknameCheckResponseDto body = new NicknameCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> duplicateNickname(){
        ResponseDto body = new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
