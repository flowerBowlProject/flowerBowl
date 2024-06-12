package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class InsertLicenseResponseDto extends ResponseDto {

    public InsertLicenseResponseDto() {
        super();
    }

    public static ResponseEntity<? super InsertLicenseResponseDto> success() {
        InsertLicenseResponseDto body = new InsertLicenseResponseDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static ResponseEntity<? super ResponseDto> duplicateLicense(){
        ResponseDto body = new ResponseDto(ResponseCode.DUPLICATE_LICENSE, ResponseMessage.DUPLICATE_LICENSE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
