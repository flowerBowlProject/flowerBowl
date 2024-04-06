package com.flowerbowl.web.dto.response.auth;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignUpResponseDTO extends ResponseDTO {

    public SignUpResponseDTO() {
        super();
    }

    public static ResponseEntity<SignUpResponseDTO> success (){
        SignUpResponseDTO body = new SignUpResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDTO> duplicatedId(){
        ResponseDTO body = new ResponseDTO(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDTO> certificationFail(){
        ResponseDTO body = new ResponseDTO(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
