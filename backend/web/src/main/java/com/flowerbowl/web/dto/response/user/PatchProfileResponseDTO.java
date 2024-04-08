package com.flowerbowl.web.dto.response.user;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PatchProfileResponseDTO extends ResponseDTO {

    public PatchProfileResponseDTO() {
        super();
    }

    public static ResponseEntity<PatchProfileResponseDTO> success() {
        PatchProfileResponseDTO body = new PatchProfileResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDTO> noExistUser() {
        ResponseDTO body = new ResponseDTO(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDTO> invalidPw() {
        ResponseDTO body = new ResponseDTO(ResponseCode.INVALID_PASSWORD, ResponseMessage.INVALID_PASSWORD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDTO> duplicateNickname() {
        ResponseDTO body = new ResponseDTO(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDTO> duplicateEmail() {
        ResponseDTO body = new ResponseDTO(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


}
