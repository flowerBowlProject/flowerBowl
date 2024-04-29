package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.*;
import com.flowerbowl.web.dto.object.lesson.*;
import com.flowerbowl.web.dto.response.lesson.*;
import com.flowerbowl.web.dto.request.lesson.CreateRequestDto;
import com.flowerbowl.web.dto.request.lesson.LessonRequestDto;
import com.flowerbowl.web.repository.LessonRepository;
import com.flowerbowl.web.repository.ReviewRepository;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.repository.lesson.*;
import com.flowerbowl.web.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final LessonRepository lessonRepository;
    private final MuziLessonLikeRepository muziLessonLikeRepository;
    private final PayRepository payRepository;
    private final PayJpaRepository payJpaRepository;
    private final LessonJpaDataRepository lessonJpaDataRepository;
    private final JpaReviewEnableRepository jpaReviewEnableRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    // 클래스 등록
    @Transactional
    @Override
    public ResponseEntity<ResponseDto> LessonCreate(CreateRequestDto createRequestDto, String userId){
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

//            User user = lessonUserRepository.findUserByUserNo(user_no);
            User user = userRepository.findByUserId(userId);
            if(user == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "userId에 대응되는 user가 없습니다."));
            }
            lesson.setUser(user);
            lesson.setLessonWriter(user.getUserNickname());

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
    public ResponseEntity<ResponseDto> LessonModify(LessonRequestDto lessonRequestDto, Long lesson_no, String userId){
        try{
            Lesson lesson = lessonsRepository.findByLesson_no(lesson_no);
            if(lesson == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "lesson_no에 대응되는 lesson이 없습니다."));
            User user = userRepository.findByUserId(userId);
            if(user == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "userId에 대응되는 user가 없습니다."));
            }
//            if(lesson.getUser().getUserId() != userId){
            if(!lesson.getUser().getUserId().equals(userId)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당하는 user가 작성한 lesson이 아닙니다."));
            }
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
    public ResponseEntity<ResponseDto> lessonDelete(Long lesson_no, String userId){
        try{
            if(!lessonJpaDataRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
            }
            User user = userRepository.findByUserId(userId);
            if(user == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "userId에 대응되는 user가 없습니다."));
            }
            Lesson lesson = lessonJpaDataRepository.findLessonByLessonNo(lesson_no);
            if(!lesson.getUser().getUserId().equals(userId)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당하는 user가 작성한 lesson이 아닙니다."));
            }
            if(lesson.getLessonDeleteStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "이미 삭제된 클래스입니다."));
            }
            lesson.setLessonDeleteStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
        }catch (Exception e){
            log.info("LessonService LessonDelete Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 클래스 조회(로그인 + 비로그인) // 즐겨찾기 여부도 포함해서
    @Override
    public ResponseEntity<? super FindAllResponseDto> findAll(Pageable pageable, Boolean loginStatus, String userId){
        try{
            Page<Lesson> page = lessonJpaDataRepository.findLessonByLessonDeleteStatusOrderByLessonNoDesc(false, pageable);
            PageInfo pageInfo = new PageInfo(page.getTotalPages(), page.getTotalElements());
            List<LessonShortDto> list = page.map(LessonShortDto::from).getContent();
            for(LessonShortDto tmp : list){
                Long lesson_no = tmp.getLesson_no();
                Long likes_no = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(lesson_no);
                tmp.setLesson_like_cnt(likes_no);
                if(loginStatus){
                    User user = userRepository.findByUserId(userId);
                    if(user == null){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당하는 userId를 가진 user가 없습니다."));
                    }
                    boolean like_status = muziLessonLikeRepository.existsByUser_UserNoAndLesson_LessonNo(user.getUserNo(), lesson_no);
                    tmp.setLesson_like_status(like_status);
                }else{
                    tmp.setLesson_like_status(false);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new FindAllResponseDto("SU", "success",pageInfo, list));
        }catch (Exception e){
            log.info("LessonService findAll exception e.getMessage() : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 모든 클래스 조회(비로그인) // 이거 위랑 합칠 수 있는지 고민
//    @Override
//    public ResponseEntity<? super FindAllResponseDto> findAllGuest(Pageable pageable){
//        try{
////            List<LessonShortDto> list = lessonJpaDataRepository.findAllByOrderByLessonNoDesc(pageable)
////                    .map(LessonShortDto::from).getContent();
//            Page<Lesson> page = lessonJpaDataRepository.findAllByOrderByLessonNoDesc(pageable);
//            PageInfo pageInfo = new PageInfo(page.getTotalPages(), page.getTotalElements());
//            List<LessonShortDto> list = page.map(LessonShortDto::from).getContent();
//            for(LessonShortDto tmp : list){
//                Long likes_num = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(tmp.getLesson_no());
//                tmp.setLesson_likes_num(likes_num);
//            }
//
//            return ResponseEntity.status(HttpStatus.OK).body(new FindAllResponseDto("SU", "success", pageInfo, list));
//        }catch (Exception e){
//            log.info("LessonService findAllGuest exception : {}",e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
//        }
//    }

    // 특정 클래스 조회(로그인) // 즐겨찾기 목록
    @Override
    public ResponseEntity<? super FindOneResponseDto> findOneResponseDto(Long lesson_no, String userId){
//    public void findOneResponseDto(Long lesson_no){
        try{
            User user = userRepository.findByUserId(userId);
            // 해당하는 lesson이 없는 경우
            if(!lessonJpaDataRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
            }
            Lesson lesson = lessonJpaDataRepository.findLessonByLessonNo(lesson_no);
            Boolean like_status = muziLessonLikeRepository.existsByUser_UserNoAndLesson_LessonNo(user.getUserNo(), lesson_no);
            LessonResponseDto lessonResponseDto = new LessonResponseDto(lesson);
            lessonResponseDto.setLesson_like_status(like_status);
            Long likes_no = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(lesson_no);
            lessonResponseDto.setLesson_like_cnt(likes_no);
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
            lessonResponseDto.setLesson_like_status(false);
            Long likes_no = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(lesson_no);
            lessonResponseDto.setLesson_like_cnt(likes_no);
            return ResponseEntity.status(HttpStatus.OK).body(new FindOneResponseDto(lessonResponseDto));
        }catch (Exception e){
            log.info("LessonService findOneGuestResponseDto Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 특정 클래스 리뷰 조회 (비로그인 + 로그인)
    public ResponseEntity<? super ReviewsResponseDto> findReviews(Long lesson_no, Pageable pageable){
        try {
            if(!lessonRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당 lesson_no을 가진 class가 존재하지 않습니다,"));
            }
            Page<LessonRv> lessonRvs = reviewRepository.findLessonRvByLesson_LessonNo(lesson_no, pageable);
            List<ReviewsShortDto> reviewsResponseDtoList = lessonRvs.map(ReviewsShortDto::from).getContent();
            return ResponseEntity.status(HttpStatus.OK).body(new ReviewsResponseDto("SU", "success", reviewsResponseDtoList));

        }catch (Exception e){
            log.info("LessonService findReviews Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }


    // 클래스 구매
    // 구매 정보 저장 + 구매 정보를 넘격줘야함
    @Override
    @Transactional
    public ResponseEntity<? super PaymentInfoResponseDto> buyLesson(Long lesson_no, String userId){
        try{
            // 해당 클래스가 없는 경우
            if(!lessonJpaDataRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
            }
            // 클래스를 구매한 유저 정보를 받아옴
            User user = userRepository.findByUserId(userId);
            if(user == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당하는 user_id를 가진 유저가 없습니다."));
            }
            // 구매 정보 저장
            Pay pay = new Pay();
            pay.setUser(user);
            // lesson // clinet에게 전송할 때도 사용
            Lesson lesson = lessonsRepository.findByLesson_no(lesson_no);
            pay.setLesson(lesson);
            pay.setPayDate(LocalDateTime.now());
            // pay_price
            pay.setPayPrice(lesson.getLessonPrice());
            // pay_code
            Long cntPay = payRepository.countAllByPayNoGreaterThan(-1L);
            String pay_code = LocalDate.now().toString() + "_" + cntPay; // ex) 2019-09-19_lesson_no;
            pay.setPayCode(pay_code);
            System.out.println(pay.getLesson().getLessonNo());

            payJpaRepository.PayCreate(pay);

            // client 에게 전송해줄 결제정보
            PayInfo payInfo = new PayInfo();
            payInfo.setMerchant_uid(pay_code); // 구매 날짜 + pk
            // 구매자에 대한 정보
            payInfo.setBuyer_name(user.getUserNickname());
            payInfo.setBuyer_tel(user.getUserPhone());
            payInfo.setBuyer_email(user.getUserEmail());
            // 클래스에 대한 정보
            payInfo.setName(lesson.getLessonTitle()); // 구매제품 이름
            payInfo.setAmount(Long.parseLong(lesson.getLessonPrice())); // 가격설정
            payInfo.kakaopay(); // 카카오페이로 결제

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

    // 클래스 즐겨찾기 등록
    @Override
    @Transactional
    public ResponseEntity<ResponseDto> LessonLike(Long lesson_no, String userId){
        try {
            User user = userRepository.findByUserId(userId);
            Lesson lesson = lessonsRepository.findByLesson_no(lesson_no);
            if(user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 userId를 가진 유저가 없습니다."));
            if(lesson == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no에 해당하는 lesson이 없습니다."));
            Boolean like_status = muziLessonLikeRepository.existsByUser_UserNoAndLesson_LessonNo(user.getUserNo(), lesson_no);
            if(like_status){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당유저는 이미 즐겨찾기를 했습니다."));
            }
            LessonLike lessonLike = new LessonLike(user, lesson);
            muziLessonLikeRepository.save(lessonLike);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
        } catch (Exception e){
            log.info("LessonService LessonLike error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 클래스 즐겨찾기 해제
    @Override
    @Transactional
    public ResponseEntity<ResponseDto> LessonUnlike(Long lesson_no, String userId){
        try {
            User user = userRepository.findByUserId(userId);
            Lesson lesson = lessonsRepository.findByLesson_no(lesson_no);
            if(user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 userId를 가진 유저가 없습니다."));
            if(lesson == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no에 해당하는 lesson이 없습니다."));
            Boolean like_status = muziLessonLikeRepository.existsByUser_UserNoAndLesson_LessonNo(user.getUserNo(), lesson_no);
            if(like_status == false) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당 유저는 즐겨찾기를 하고 있지 않습니다."));
            LessonLike lessonLike = muziLessonLikeRepository.findLessonLikeByLesson_LessonNoAndUser_UserNo(lesson_no, user.getUserNo());
            muziLessonLikeRepository.delete(lessonLike);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
        } catch (Exception e){
            log.info("LessonService LessonUnlike error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
}
