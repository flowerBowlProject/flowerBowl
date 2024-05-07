package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.object.lesson.LessonShortDto;
import com.flowerbowl.web.dto.request.lesson.CreateRequestDto;
import com.flowerbowl.web.dto.request.lesson.LessonRequestDto;
import com.flowerbowl.web.dto.response.lesson.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface LessonService {
    ResponseEntity<ResponseDto> LessonCreate(CreateRequestDto createRequestDto, String userId);
    @Transactional
    ResponseEntity<ResponseDto> LessonModify(LessonRequestDto lessonRequestDto, Long lesson_no, String userId);
    ResponseEntity<ResponseDto> lessonDelete(Long lesson_no, String userId);
    ResponseEntity<? super FindAllResponseDto> findAll(Pageable pageable, Boolean loginStatus, String userId);
//    ResponseEntity<? super FindAllResponseDto> findAllGuest(Pageable pageable);
    ResponseEntity<? super FindOneResponseDto> findOneResponseDto(Boolean loginStatus ,Long lesson_no, String userId);
//    ResponseEntity<? super FindOneResponseDto> findOneGuestResponseDto(Long lesson_no);
    ResponseEntity<? super ReviewsResponseDto> findReviews(Long lesson_no, Pageable pageable);
    ResponseEntity<? super PaymentInfoResponseDto> buyLesson(Long lesson_no, String userId);
    ResponseEntity<ResponseDto> LessonLike(Long lesson_no, String userId);
    ResponseEntity<ResponseDto> LessonUnlike(Long lesson_no, String userId);
    ResponseEntity<? super FindAllResponseDto> getMostLikedLesson();
    ResponseEntity<? super LessonCategoryResponseDto> getLessonsCategory(Boolean loginStatus, String koreaName, String userId, Pageable pageable);
}
