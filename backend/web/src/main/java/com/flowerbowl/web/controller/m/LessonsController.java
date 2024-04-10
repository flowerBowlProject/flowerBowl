package com.flowerbowl.web.controller.m;

import com.flowerbowl.web.dto.m.ResponseDto;
import com.flowerbowl.web.dto.m.lesson.*;
import com.flowerbowl.web.dto.m.search.LessonRequestDto;
import com.flowerbowl.web.service.m.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonsController {

    private final LessonService lessonService;

//    @AuthenticationPrincipal

    // 클래스 등록
    // POST
    // 일단은 map으로 받고 나중에 DTO로 바뀔것
    @PostMapping(value = "")
    public ResponseEntity<ResponseDto> lessonsRegister(@RequestBody CreateRequestDto createRequestDto){
        return lessonService.LessonCreate(createRequestDto);
    }

    // 클래스 수정
    // PUT
    @PutMapping(value = "/{lesson_no}")
    public ResponseEntity<ResponseDto> lessonsModify(@RequestBody LessonRequestDto lessonRequestDto, @PathVariable Long lesson_no){
        return lessonService.LessonModify(lessonRequestDto, lesson_no);
    }
    // 클래스 삭제
    // PUT
    @PutMapping(value = "")
    public ResponseEntity<ResponseDto> lessonsDelete(@RequestBody DeleteRequestDto deleteRequestDto){
        return lessonService.lessonDelete(deleteRequestDto.getLesson_no());
    }

    // 전체 클래스 조회 (로그인)
    // GET
    @GetMapping("")
    public ResponseEntity<? super FindAllResponseDto> lessonsFindAll(@PageableDefault(page = 0, size = 8) Pageable pageable){
        Long user_no = 1L;
        return lessonService.findAll(user_no, pageable);
    }

    // 전체 클래스 조회 (비로그인)
    // GET
    @GetMapping("/guest") // guest
    public ResponseEntity<? super FindAllResponseDto> lessonsFindAllGuest(@PageableDefault(page = 0, size = 8) Pageable pageable){
//   public FindAllResponseDto lessonsFindAllGuest(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size){
        return lessonService.findAllGuest(pageable);
    }

    // 특정 클래스 조회 (로그인)
    // GET
    @GetMapping("/{lesson_no}")
    public ResponseEntity<? super FindOneResponseDto> lessonsFindOne(@PathVariable Long lesson_no){
        Long user_no = 1L; // JWT 토큰으로 부터 받아와야함
        return lessonService.findOneResponseDto(lesson_no, user_no);
    }
    // 특정 클래스 조회 (비로그인)
    // GET
    @GetMapping("/{lesson_no}/guest")
    public ResponseEntity<? super FindOneResponseDto> lessonsFindOneGuest(@PathVariable Long lesson_no){
        return lessonService.findOneGuestResponseDto(lesson_no);
    }

    // 클래스 구매 // review_enable에도 넣어줘야함
    // POST
    @PostMapping(value = "/buy")
    public ResponseEntity<? super PaymentInfoResponseDto> lessonsBuy(@RequestBody PaymentInfoRequestDto paymentInfoRequestDto){
        Long user_no = 1L; // JWT 토큰으로 부터 값을 받아와야함
        return lessonService.buyLesson(paymentInfoRequestDto.getLesson_no(), user_no);
    }
}
