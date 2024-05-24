package com.flowerbowl.web.dto.response.user;

import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PatchWdResponseDto extends ResponseDto {


    public PatchWdResponseDto() {
        super();
    }

    public static ResponseEntity<? super PatchWdResponseDto> success() {
        PatchWdResponseDto body = new PatchWdResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
