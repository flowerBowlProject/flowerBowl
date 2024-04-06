package com.flowerbowl.web.dto.response.auth;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDTO extends ResponseDTO {

    private String access_token;
    private int expirationTime;
    private Long user_no;

    private SignInResponseDTO(String access_token, Long user_no) {
        super();
        this.access_token = access_token;
        this.expirationTime = 3600;
        this.user_no = user_no;
    }

    public static ResponseEntity<SignInResponseDTO> success(String access_token, Long user_no) {
        SignInResponseDTO body = new SignInResponseDTO(access_token, user_no);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + access_token);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(body);
    }

    public static ResponseEntity<ResponseDTO> signInFail() {
        ResponseDTO body = new ResponseDTO(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
