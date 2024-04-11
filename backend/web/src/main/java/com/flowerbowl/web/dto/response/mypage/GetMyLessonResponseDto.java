package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.dto.object.mypage.MyLessons;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetMyLessonResponseDto extends ResponseDto {

    private List<MyLessons> myLessons;

    public GetMyLessonResponseDto(List<MyLessons> myLessons) {
        super();
        this.myLessons = myLessons;
    }

    public static ResponseEntity<? super GetMyLessonResponseDto> success(List<MyLessons> myLessons) {
        GetMyLessonResponseDto body = new GetMyLessonResponseDto(myLessons);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
