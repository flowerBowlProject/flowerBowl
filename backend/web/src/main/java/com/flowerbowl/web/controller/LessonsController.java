package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.request.lesson.*;
import com.flowerbowl.web.dto.response.lesson.*;
import com.flowerbowl.web.handler.*;
import com.flowerbowl.web.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class LessonsController {
    private final LessonService lessonService;

//    @AuthenticationPrincipal

    // 클래스 등록
    // POST
    @PostMapping(value = "/lessons")
    public ResponseEntity<ResponseDto> lessonsRegister(@AuthenticationPrincipal String userId, @RequestBody CreateRequestDto createRequestDto){
        try{
            return lessonService.LessonCreate(createRequestDto, userId);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "userId에 대응되는 user가 없습니다."));
        }catch (FileSizeNotMatchException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("ISE", "file_oname과 file_sname의 사이즈가 다릅니다."));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 클래스 수정
    // PUT
    @PutMapping(value = "/lessons/{lesson_no}")
    public ResponseEntity<ResponseDto> lessonsModify(@AuthenticationPrincipal String userId, @RequestBody LessonRequestDto lessonRequestDto, @PathVariable(value = "lesson_no") Long lesson_no){
        try{
            return lessonService.LessonModify(lessonRequestDto, lesson_no, userId);
        }catch (LessonNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "lesson_no에 대응되는 lesson이 없습니다."));
        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "userId에 대응되는 user가 없습니다."));
        }catch (DoesNotMatchException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당하는 user가 작성한 lesson이 아닙니다."));
        }catch (FileSizeNotMatchException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "fileOname과 fileSname의 크기 다릅니다."));
        }catch (LessonFileNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "삭제할 fileOname에 맞는 파일명이 DB에 존재하지 않습니다."));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 클래스 삭제
    // PUT
    @PutMapping(value = "/lessons")
    public ResponseEntity<ResponseDto> lessonsDelete(@AuthenticationPrincipal String userId, @RequestBody DeleteRequestDto deleteRequestDto){
        try {
            return lessonService.lessonDelete(deleteRequestDto.getLesson_no(), userId);
        }catch (LessonNotFoundException e){
            log.info("LessonService lessonDelete Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
        } catch (UserNotFoundException e){
            log.info("LessonService lessonDelete Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "userId에 대응되는 user가 없습니다."));
        }catch (DoesNotMatchException e){
            log.info("LessonService lessonDelete Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당하는 user가 작성한 lesson이 아닙니다."));
        }catch (LessonAlreadyDeletedException e){
            log.info("LessonService lessonDelete Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "이미 삭제된 클래스입니다."));
        }
        catch (Exception e){
            log.info("LessonService LessonDelete Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 전체 클래스 조회 (로그인)
    // GET
    @GetMapping("/user/lessons")
    public ResponseEntity<? super FindAllResponseDto> lessonsFindAll(@AuthenticationPrincipal String userId, @PageableDefault(page = 0, size = 8) Pageable pageable){
        return lessonService.findAll(pageable, true, userId);
    }

    // 전체 클래스 조회 (비로그인)
    // GET
    @GetMapping("/guest/lessons") // guest
    public ResponseEntity<? super FindAllResponseDto> lessonsFindAllGuest(@PageableDefault(page = 0, size = 8) Pageable pageable){
//   public FindAllResponseDto lessonsFindAllGuest(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size){
        return lessonService.findAll(pageable, false, "guest");
    }

    // 특정 클래스 조회 (로그인)
    // GET
    @GetMapping("/user/lessons/{lesson_no}")
    public ResponseEntity<? super FindOneResponseDto> lessonsFindOne(@AuthenticationPrincipal String userId, @PathVariable Long lesson_no){
        return lessonService.findOneResponseDto(true, lesson_no, userId);
    }
    // 특정 클래스 조회 (비로그인)
    // GET
    @GetMapping("/guest/lessons/{lesson_no}")
    public ResponseEntity<? super FindOneResponseDto> lessonsFindOneGuest(@PathVariable(value = "lesson_no") Long lesson_no){
//        return lessonService.findOneGuestResponseDto(lesson_no);
        return lessonService.findOneResponseDto(false, lesson_no, "guest");
    }
    // 특정 클래스 리뷰조회 (로그인 + 비로그인) // GET
    @GetMapping("/guest/lessons/reviews")
    public ResponseEntity<? super ReviewsResponseDto> lessonsReviews(@RequestParam("lessonNo") Long lesson_no, @PageableDefault(page = 0, size = 8) Pageable pageable){
        return lessonService.findReviews(lesson_no, pageable);
    }

    // 클래스 구매 // review_enable에도 넣어줘야함
    // POST
    @PostMapping(value = "/user/lessons/payments")
    public ResponseEntity<? super PaymentInfoResponseDto> lessonsBuy(@AuthenticationPrincipal String userId, @RequestBody PaymentInfoRequestDto paymentInfoRequestDto){
        try {
            return lessonService.buyLesson(paymentInfoRequestDto.getLesson_no(), userId);
        }catch (LessonNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당하는 user_id를 가진 유저가 없습니다."));
        }catch (LessonAlreadyPaidException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "이미 구매한 클래스입니다."));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 결제 오류시 결제 테이블에서 삭제
    @PutMapping(value = "/user/lessons")
    public ResponseEntity<ResponseDto> lessonBuyErrorController(@AuthenticationPrincipal String userId, @RequestBody PayErrorRequestDto payErrorRequestDto){
        try{
            return lessonService.LessonBuyErrorHandler(payErrorRequestDto, userId);
        } catch (UserNotFoundException userNotFoundException){
            log.info("error msg : {}", userNotFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "user not found exception"));
        } catch (PayNotFindException e){
            log.info("error msg : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "pay not found exception"));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "bad request"));
        }
    }

    // 클래스 즐겨찾기 등록 // POST
    @PostMapping(value = "/user/lessons/like")
    public ResponseEntity<ResponseDto> lessonLike(@AuthenticationPrincipal String userId, @RequestBody LessonLikeRequestDto lessonLikeRequestDto){
        return lessonService.LessonLike(lessonLikeRequestDto.getLesson_no(), userId);
    }

    // 클래스 즐겨찾기 해제 // DELETE
    @DeleteMapping(value = "/user/lessons/like/{lesson_no}")
    public ResponseEntity<ResponseDto> lessonUnlike(@AuthenticationPrincipal String userId, @PathVariable(value = "lesson_no") Long lesson_no){
        return lessonService.LessonUnlike(lesson_no, userId);
    }
    // 인기 클래스 조회 // 북마크가 가장 많은 5개
    @GetMapping("/guest/lessons/like")
    public ResponseEntity<? super FindAllResponseDto> getMostLikedLesson(){
//    public ResponseEntity<? super FindAllResponseDto> getMostLikedLesson(@PageableDefault(page = 0, size = 5) Pageable pageable){
        return lessonService.getMostLikedLesson();
    }

    // 카테고리별 클래스 조회 로그인
    @GetMapping(value = "/user/lessons/category") // api/user/lessons/category?category="국"&page=0&size=6
    public ResponseEntity<? super LessonCategoryResponseDto> lessonCategoryLogin(@AuthenticationPrincipal String userId, @RequestParam(value = "category") String koreanName, @PageableDefault(page = 0, size = 10) Pageable pageable){
        return lessonService.getLessonsCategory(true, koreanName, userId, pageable);
    }
    // 카테고리별 클래스 조회 비로그인
    @GetMapping(value = "/guest/lessons/category") // api/user/lessons/category?category="국"&page=0&size=6
    public ResponseEntity<? super LessonCategoryResponseDto> lessonCategoryLogin(@RequestParam(value = "category") String koreanName, @PageableDefault(page = 0, size = 10) Pageable pageable){
        log.info("guest category : {}", koreanName);
        return lessonService.getLessonsCategory(false, koreanName, "guest", pageable);
    }
}
