package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.dto.object.mypage.PayLessons;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetPayLessonResponseDto extends ResponseDto {

    private List<PayLessons> payLessons;

    public GetPayLessonResponseDto(List<PayLessons> payLessons) {
        super();
        this.payLessons = payLessons;
    }

    public static ResponseEntity< ? super GetPayLessonResponseDto> success(List<PayLessons> payLessons){
        GetPayLessonResponseDto body = new GetPayLessonResponseDto(payLessons);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
