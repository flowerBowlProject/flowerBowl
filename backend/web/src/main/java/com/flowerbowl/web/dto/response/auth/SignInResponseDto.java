package com.flowerbowl.web.dto.response.auth;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends ResponseDto {

    private String access_token;
    private int expirationTime;
    private Long user_no;

    private SignInResponseDto(String access_token, Long user_no) {
        super();
        this.access_token = access_token;
        this.expirationTime = 3600;
        this.user_no = user_no;
    }

    public static ResponseEntity<SignInResponseDto> success(String access_token, Long user_no) {
        SignInResponseDto body = new SignInResponseDto(access_token, user_no);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + access_token);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto body = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
