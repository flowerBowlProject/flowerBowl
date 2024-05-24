package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.dto.object.mypage.Pays;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetPaysResponseDto extends ResponseDto {

    private List<Pays> pays;

    public GetPaysResponseDto(List<Pays> pays) {
        super();
        this.pays = pays;
    }

    public static ResponseEntity<? super GetPaysResponseDto> success(List<Pays> pays) {
        GetPaysResponseDto body = new GetPaysResponseDto(pays);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
