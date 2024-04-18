package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.lesson.CreateRequestDto;
import com.flowerbowl.web.dto.request.lesson.LessonRequestDto;
import com.flowerbowl.web.dto.response.lesson.FindAllResponseDto;
import com.flowerbowl.web.dto.response.lesson.FindOneResponseDto;
import com.flowerbowl.web.dto.response.lesson.PaymentInfoResponseDto;
import com.flowerbowl.web.dto.response.lesson.ResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface LessonService {
    ResponseEntity<ResponseDto> LessonCreate(CreateRequestDto createRequestDto, String userId);
    ResponseEntity<ResponseDto> LessonModify(LessonRequestDto lessonRequestDto, Long lesson_no, String userId);
    ResponseEntity<ResponseDto> lessonDelete(Long lesson_no, String userId);
    ResponseEntity<? super FindAllResponseDto> findAll(Pageable pageable, String userId);
    ResponseEntity<? super FindAllResponseDto> findAllGuest(Pageable pageable);
    ResponseEntity<? super FindOneResponseDto> findOneResponseDto(Long lesson_no, String userId);
    ResponseEntity<? super FindOneResponseDto> findOneGuestResponseDto(Long lesson_no);
    ResponseEntity<? super PaymentInfoResponseDto> buyLesson(Long lesson_no, String userId);
    ResponseEntity<ResponseDto> LessonLike(Long lesson_no, String userId);
    ResponseEntity<ResponseDto> LessonUnlike(Long lesson_no, String userId);
}