package com.flowerbowl.web.dto.response.auth;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class NicknameCheckResponseDTO extends ResponseDTO {

    private NicknameCheckResponseDTO(){
        super();
    }

    public static ResponseEntity<NicknameCheckResponseDTO> success(){
        NicknameCheckResponseDTO body = new NicknameCheckResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDTO> duplicateNickname(){
        ResponseDTO body = new ResponseDTO(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
