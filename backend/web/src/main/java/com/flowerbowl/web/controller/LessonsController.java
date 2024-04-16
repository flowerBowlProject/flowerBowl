package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.response.lesson.ResponseDto;
import com.flowerbowl.web.dto.request.lesson.CreateRequestDto;
import com.flowerbowl.web.dto.request.lesson.DeleteRequestDto;
import com.flowerbowl.web.dto.request.lesson.LessonRequestDto;
import com.flowerbowl.web.dto.request.lesson.PaymentInfoRequestDto;
import com.flowerbowl.web.dto.response.lesson.FindAllResponseDto;
import com.flowerbowl.web.dto.response.lesson.FindOneResponseDto;
import com.flowerbowl.web.dto.response.lesson.PaymentInfoResponseDto;
import com.flowerbowl.web.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LessonsController {
    private final LessonService lessonService;

//    @AuthenticationPrincipal

    // 클래스 등록
    // POST
    @PostMapping(value = "/lessons")
    public ResponseEntity<ResponseDto> lessonsRegister(@AuthenticationPrincipal String userId, @RequestBody CreateRequestDto createRequestDto){
//        System.out.println("lessons 등록 test");
        return lessonService.LessonCreate(createRequestDto, userId);
    }

    // 클래스 수정
    // PUT
    @PutMapping(value = "/lessons/{lesson_no}")
    public ResponseEntity<ResponseDto> lessonsModify(@AuthenticationPrincipal String userId, @RequestBody LessonRequestDto lessonRequestDto, @PathVariable Long lesson_no){
        return lessonService.LessonModify(lessonRequestDto, lesson_no, userId);
    }
    // 클래스 삭제
    // PUT
    @PutMapping(value = "/lessons")
    public ResponseEntity<ResponseDto> lessonsDelete(@AuthenticationPrincipal String userId, @RequestBody DeleteRequestDto deleteRequestDto){
        return lessonService.lessonDelete(deleteRequestDto.getLesson_no(), userId);
    }

    // 전체 클래스 조회 (로그인)
    // GET
    @GetMapping("/user/lessons")
    public ResponseEntity<? super FindAllResponseDto> lessonsFindAll(@AuthenticationPrincipal String userId, @PageableDefault(page = 1, size = 8) Pageable pageable){
        return lessonService.findAll(pageable, userId);
    }

    // 전체 클래스 조회 (비로그인)
    // GET
    @GetMapping("/guest/lessons") // guest
    public ResponseEntity<? super FindAllResponseDto> lessonsFindAllGuest(@PageableDefault(page = 1, size = 8) Pageable pageable){
//   public FindAllResponseDto lessonsFindAllGuest(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size){
        return lessonService.findAllGuest(pageable);
    }

    // 특정 클래스 조회 (로그인)
    // GET
    @GetMapping("/user/lessons/{lesson_no}")
    public ResponseEntity<? super FindOneResponseDto> lessonsFindOne(@AuthenticationPrincipal String userId, @PathVariable Long lesson_no){
        return lessonService.findOneResponseDto(lesson_no, userId);
    }
    // 특정 클래스 조회 (비로그인)
    // GET
    @GetMapping("/guest/lessons/{lesson_no}")
    public ResponseEntity<? super FindOneResponseDto> lessonsFindOneGuest(@PathVariable Long lesson_no){
        return lessonService.findOneGuestResponseDto(lesson_no);
    }

    // 클래스 구매 // review_enable에도 넣어줘야함
    // POST
    @PostMapping(value = "/lessons/buy")
    public ResponseEntity<? super PaymentInfoResponseDto> lessonsBuy(@AuthenticationPrincipal String userId, @RequestBody PaymentInfoRequestDto paymentInfoRequestDto){
        return lessonService.buyLesson(paymentInfoRequestDto.getLesson_no(), userId);
    }
}
