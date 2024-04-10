package com.flowerbowl.web.lesson.lessonDTO;

import com.flowerbowl.web.commonDTO.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindOneResponseDto extends ResponseDto{
    private String  code;
    private String message;
    private LessonResponseDto lesson;

    public FindOneResponseDto(LessonResponseDto lessonResponseDto){
        code = "SU";
        message = "success";
        this.lesson = lessonResponseDto;
    }
    public FindOneResponseDto(){
        code = "FA";
        message = "Bad Request";
    }
}
