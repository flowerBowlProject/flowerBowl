package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.dto.object.lesson.LessonShortDto;
import com.flowerbowl.web.domain.Lesson;
import com.flowerbowl.web.domain.Pay;
import com.flowerbowl.web.domain.ReviewEnable;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.response.lesson.ResponseDto;
import com.flowerbowl.web.dto.object.lesson.LessonResponseDto;
import com.flowerbowl.web.dto.object.lesson.PayInfo;
import com.flowerbowl.web.dto.request.lesson.CreateRequestDto;
import com.flowerbowl.web.dto.request.lesson.LessonRequestDto;
import com.flowerbowl.web.dto.response.lesson.FindAllResponseDto;
import com.flowerbowl.web.dto.response.lesson.FindOneResponseDto;
import com.flowerbowl.web.dto.response.lesson.PaymentInfoResponseDto;
import com.flowerbowl.web.repository.lesson.*;
import com.flowerbowl.web.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonsRepository lessonsRepository;
    private final LessonUserRepository lessonUserRepository;
    private final MuziLessonLikeRepository muziLessonLikeRepository;
    private final PayRepository payRepository;
    private final PayJpaRepository payJpaRepository;
    private final LessonJpaDataRepository lessonJpaDataRepository;
    private final JpaReviewEnableRepository jpaReviewEnableRepository;

    // 클래스 등록
    // JWT 토큰 parsing해서 user_id의 정보를 따로 얻어와야함
    @Transactional
    @Override
    public ResponseEntity<ResponseDto> LessonCreate(CreateRequestDto createRequestDto){
        try{
            Lesson lesson = new Lesson();
            lesson.setLessonTitle(createRequestDto.getLesson_title());
            lesson.setLessonPrice(createRequestDto.getLesson_price());
            lesson.setLessonAddr(createRequestDto.getLesson_addr());
            lesson.setLessonLongitude(createRequestDto.getLesson_longitude());
            lesson.setLessonLatitude(createRequestDto.getLesson_latitude());
            lesson.setLessonCategory(createRequestDto.getLesson_category());
            lesson.setLessonURL(createRequestDto.getLesson_URL());
            // lesson_date // 서버에서 날짜 받아서 넣기
            lesson.setLessonDate(LocalDate.now());
            lesson.setLessonStart(createRequestDto.getLesson_start());
            lesson.setLessonEnd(createRequestDto.getLesson_end());
            lesson.setLessonDeleteStatus(false);
            lesson.setLessonViews(0);

            lesson.setLessonContent(createRequestDto.getLesson_content());
            lesson.setLessonSname(createRequestDto.getLesson_sname());
            lesson.setLessonOname(createRequestDto.getLesson_oname());

            // user_no : token으로 부터 받아오기
            Long user_no = 1L;
            User user = lessonUserRepository.findUserByUserNo(user_no);
            lesson.setUser(user);
            lesson.setLessonWriter(user.getUserNickname()); // 1L을 바꿔줘야함

            lessonsRepository.LessonCreate(lesson);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
        }catch (Exception e){
            log.info("LessonService LessonCreate Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 클래스 수정
    @Transactional
    @Override
    public ResponseEntity<ResponseDto> LessonModify(LessonRequestDto lessonRequestDto, Long lesson_no){
        try{
            Lesson lesson = lessonsRepository.findByLesson_no(lesson_no);
            lesson.setLessonTitle(lessonRequestDto.getLesson_title());
            lesson.setLessonPrice(lessonRequestDto.getLesson_price());
            lesson.setLessonSname(lessonRequestDto.getLesson_sname());
            lesson.setLessonOname(lessonRequestDto.getLesson_oname());
            lesson.setLessonAddr(lessonRequestDto.getLesson_addr());
            lesson.setLessonLongitude(lessonRequestDto.getLesson_longitude());
            lesson.setLessonLatitude(lessonRequestDto.getLesson_latitude());
            lesson.setLessonCategory(lessonRequestDto.getLesson_category());
            lesson.setLessonURL(lessonRequestDto.getLesson_URL());
            lesson.setLessonContent(lessonRequestDto.getLesson_content());
//            ResponseDto responseDto = new ResponseDto();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
        }catch (Exception e){
            log.info("LessonService LessonModify Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 클래스 삭제 // 실제 삭제가 아닌 값의 변경
    @Transactional
    @Override
    public ResponseEntity<ResponseDto> lessonDelete(Long lesson_no){
        try{
            if(!lessonJpaDataRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
            }
            Lesson lesson = lessonJpaDataRepository.findLessonByLessonNo(lesson_no);
            lesson.setLessonDeleteStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
        }catch (Exception e){
            log.info("LessonService LessonDelete Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 모든 클래스 조회(로그인) // 즐겨찾기 여부도 포함해서
    @Transactional
    @Override
    public ResponseEntity<? super FindAllResponseDto> findAll(Long user_no, Pageable pageable){
        try{
            List<LessonShortDto> list = lessonJpaDataRepository.findAllByOrderByLessonNoDesc(pageable)
                    .map(LessonShortDto::guestFrom).getContent();
            for(LessonShortDto tmp : list){
                Long lesson_no = tmp.getLesson_no();
                Long likes_no = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(lesson_no);
                tmp.setLesson_likes_num(likes_no);
                boolean like_status = muziLessonLikeRepository.existsByUser_UserNoAndLesson_LessonNo(user_no, lesson_no);
                tmp.setLesson_likes_status(like_status);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new FindAllResponseDto("SU", "success", list));
        }catch (Exception e){
            log.info("LessonService findAll exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 모든 클래스 조회(비로그인) // 이거 위랑 합칠 수 있는지 고민
    @Transactional
    @Override
    public ResponseEntity<? super FindAllResponseDto> findAllGuest(Pageable pageable){
        try{
            List<LessonShortDto> list = lessonJpaDataRepository.findAllByOrderByLessonNoDesc(pageable)
                    .map(LessonShortDto::guestFrom).getContent();

            for(LessonShortDto tmp : list){
                Long likes_num = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(tmp.getLesson_no());
                tmp.setLesson_likes_num(likes_num);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new FindAllResponseDto("SU", "success", list));
        }catch (Exception e){
            log.info("LessonService findAllGuest exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 특정 클래스 조회(로그인) // 즐겨찾기 목록
    @Override
    public ResponseEntity<? super FindOneResponseDto> findOneResponseDto(Long lesson_no, Long user_no){
//    public void findOneResponseDto(Long lesson_no){
        try{
            // 해당하는 lesson이 없는 경우
            if(!lessonJpaDataRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
            }
            Lesson lesson = lessonJpaDataRepository.findLessonByLessonNo(lesson_no);
            Boolean like_status = muziLessonLikeRepository.existsByUser_UserNoAndLesson_LessonNo(user_no, lesson_no);
            LessonResponseDto lessonResponseDto = new LessonResponseDto(lesson);
            lessonResponseDto.setLesson_likes_status(like_status);
            Long likes_no = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(lesson_no);
            lessonResponseDto.setLesson_likes_num(likes_no);
            return ResponseEntity.status(HttpStatus.OK).body(new FindOneResponseDto(lessonResponseDto));
        }catch (Exception e){
            log.info("LessonService findOneResponseDto Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 특정 클래스 조회(비로그인)
    @Override
    public ResponseEntity<? super FindOneResponseDto> findOneGuestResponseDto(Long lesson_no){
        try{
            // 해당하는 lesson이 없는 경우
            if(!lessonJpaDataRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
            }
            Lesson lesson = lessonJpaDataRepository.findLessonByLessonNo(lesson_no);
            LessonResponseDto lessonResponseDto = new LessonResponseDto(lesson);
            lessonResponseDto.setLesson_likes_status(false);
            Long likes_no = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(lesson_no);
            lessonResponseDto.setLesson_likes_num(likes_no);
            return ResponseEntity.status(HttpStatus.OK).body(new FindOneResponseDto(lessonResponseDto));
        }catch (Exception e){
            log.info("LessonService findOneGuestResponseDto Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 클래스 구매
    // 구매 정보 저장 + 구매 정보를 넘격줘야함
    @Override
    @Transactional
    public ResponseEntity<? super PaymentInfoResponseDto> buyLesson(Long lesson_no, Long user_no){
        try{
            // 해당 클래스가 없는 경우
            if(!lessonJpaDataRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
            }
            // 구매 정보 저장
            Pay pay = new Pay();
            // user
            User user = lessonUserRepository.findUserByUserNo(user_no);
            pay.setUser(user);
            // lesson
            Lesson lesson = lessonsRepository.findByLesson_no(lesson_no);
            pay.setLesson(lesson);
            pay.setPayDate(LocalDateTime.now());
            // pay_price
            pay.setPayPrice(lesson.getLessonPrice());
            // pay_code
            Long cntPay = payRepository.countAllByPayNoGreaterThan(-1L);
            String pay_code = LocalDate.now().toString() + "_" + cntPay;
            pay.setPayCode(pay_code);
            System.out.println(pay.getLesson().getLessonNo());

            payJpaRepository.PayCreate(pay);

            // client 에게 전송해줄 결제정보
            PayInfo payInfo = new PayInfo();
            payInfo.setOrder_no(pay_code); // 구매 날짜 + pk
            payInfo.setUser_nickname(user.getUserNickname());
            payInfo.setUser_phone_number(user.getUserPhone());
            payInfo.setUser_email(user.getUserEmail());
            payInfo.kakaopay();

            // review_enable table에 넣어줌
//            jpaDataReviewEnable.save(new ReviewEnable())
            ReviewEnable reviewEnable = new ReviewEnable();
            reviewEnable.setReviewEnable(false);
            reviewEnable.setUser(user);
            reviewEnable.setLesson(lesson);
            jpaReviewEnableRepository.save(reviewEnable);

            return ResponseEntity.status(HttpStatus.OK).body(new PaymentInfoResponseDto(payInfo));

        }catch (Exception e){
            log.info("LessonService LessonDelete Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

}
