package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DeletePayResponseDto extends ResponseDto {

    public DeletePayResponseDto() {
        super();
    }

    public static ResponseEntity<? super DeletePayResponseDto> success(){
        DeletePayResponseDto body = new DeletePayResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
