package com.flowerbowl.web.dto.response.auth;


import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class IdCheckResponseDTO extends ResponseDTO {

    private IdCheckResponseDTO(){
        super();
    }

    public static ResponseEntity<IdCheckResponseDTO> success(){
        IdCheckResponseDTO body = new IdCheckResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDTO> duplicateId(){
        ResponseDTO body = new ResponseDTO(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
