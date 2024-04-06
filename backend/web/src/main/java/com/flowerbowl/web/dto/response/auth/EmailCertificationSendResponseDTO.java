package com.flowerbowl.web.dto.response.auth;


import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EmailCertificationSendResponseDTO extends ResponseDTO {

    private EmailCertificationSendResponseDTO() {
        super();
    }

    public static ResponseEntity<EmailCertificationSendResponseDTO> success() {
        EmailCertificationSendResponseDTO body = new EmailCertificationSendResponseDTO();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDTO> duplicateEmail() {
        ResponseDTO body = new ResponseDTO(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDTO> mailSendFail() {
        ResponseDTO body = new ResponseDTO(ResponseCode.MAIL_FAIL, ResponseMessage.MAIL_FAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
