package com.flowerbowl.web.dto.response.mypage;

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

}
