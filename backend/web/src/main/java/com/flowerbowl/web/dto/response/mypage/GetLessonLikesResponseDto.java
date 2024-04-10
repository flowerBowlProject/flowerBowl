package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.object.LikeLessonList;
import com.flowerbowl.web.dto.object.LikeLessons;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Getter
public class GetLessonLikesListResponseDto extends ResponseDto {

    private List<LikeLessons> likeLessonList;

    public GetLessonLikesListResponseDto(List<LikeLessons> likeLessonList) {
        super();
        this.likeLessonList = likeLessonList;
    }

    public static ResponseEntity<GetLessonLikesListResponseDto> success(List<LikeLessons> likeLessonList) {
        GetLessonLikesListResponseDto body = new GetLessonLikesListResponseDto(likeLessonList);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistLesson() {
        ResponseDto body = new ResponseDto(ResponseCode.NOT_EXIST_LESSON, ResponseMessage.NOT_EXIST_LESSON);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
