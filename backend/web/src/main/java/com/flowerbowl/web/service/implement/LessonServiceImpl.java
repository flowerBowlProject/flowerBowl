package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.*;
import com.flowerbowl.web.dto.object.lesson.*;
import com.flowerbowl.web.dto.response.lesson.*;
import com.flowerbowl.web.dto.request.lesson.CreateRequestDto;
import com.flowerbowl.web.dto.request.lesson.LessonRequestDto;
import com.flowerbowl.web.repository.*;
import com.flowerbowl.web.repository.lesson.*;
import com.flowerbowl.web.repository.lesson.PayRepository;
import com.flowerbowl.web.service.ImageService;
import com.flowerbowl.web.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonsRepository lessonsRepository;
    private final LessonRepository lessonRepository;
    private final MuziLessonLikeRepository muziLessonLikeRepository;
    private final LessonJpaDataRepository lessonJpaDataRepository;
    private final ReviewEnableRepository reviewEnableRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final PayRepository payRepository;
    private final ImageService imageService;
    private final LessonFileRepository lessonFileRepository;
    private final LessonLikeRepository lessonLikeRepository;
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

            // temp/thumbnail/파일명
            String lesson_oname = createRequestDto.getLesson_oname();
            if(lesson_oname != null && lesson_oname.trim().isEmpty()){
                lesson_oname = null;
            }
            String lesson_sname = createRequestDto.getLesson_sname();
            if(lesson_sname != null && lesson_sname.trim().isEmpty()){
                lesson_sname = null;
            }
            if(lesson_sname != null && lesson_oname != null){
                String new_lesson_oname = "lessonThumbnail/" + lesson_oname.split("/")[2];

                imageService.copyS3(lesson_oname, new_lesson_oname);

                lesson_oname = new_lesson_oname;
                lesson_sname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + new_lesson_oname;
            }

            lesson.setLessonSname(lesson_sname);
            lesson.setLessonOname(lesson_oname);

            // lesson file에 관한 부분
            List<String> file_oname = null;
            List<String> file_sname = null;
            String content = createRequestDto.getLesson_content();

            // 둘다 비어있지 않은 경우
            if(!CollectionUtils.isEmpty(createRequestDto.getLesson_file_oname()) && !CollectionUtils.isEmpty(createRequestDto.getLesson_file_sname())){
                log.info("lessonCreate 둘다 비어있지 않은 경우");
                file_sname = new ArrayList<>();
                file_oname = new ArrayList<>();

                for(String source : createRequestDto.getLesson_file_sname()){
                    log.info("before contain source");
                    if(content.contains(source)){
                        log.info("lessonCreate content.contains(source) : source : {}", source);
                        String fileName = source.split("/")[source.split("/").length - 1];

                        String oldFileOname = "temp/content/" + fileName;
                        String newFileOname = "lesson/" + fileName;

                        imageService.copyS3(oldFileOname, newFileOname);

                        String newFileSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newFileOname;

                        content = content.replace(source, newFileSname);

                        file_oname.add(newFileOname);
                        file_sname.add(newFileSname);
                    }else{
                        log.info("lessonService/lessonCreate request로 file_name은 왔지만 content에 포함x, : {}", source);
                    }
                }
            }

            // 바꾼걸로 넣어줌
            lesson.setLessonContent(content);
//            User user = lessonUserRepository.findUserByUserNo(user_no);
            User user = userRepository.findByUserId(userId);
            if(user == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "userId에 대응되는 user가 없습니다."));
            }
            lesson.setUser(user);
            lesson.setLessonWriter(user.getUserNickname());

            Lesson lessonResult = lessonRepository.save(lesson);

            // DB file에 넣어줌
            if(file_oname != null && file_sname != null && (file_oname.size() != file_sname.size())){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "file_oname과 file_sname의 사이즈가 다릅니다."));
            }

            LessonFile lessonFile = new LessonFile();
            lessonFile.setLesson(lessonResult);
            int iterNum = 0;
            if(file_oname == null){
                iterNum = 0;
                log.info("file_oname0 : null");
            }else{
                iterNum = file_oname.size();
                log.info("file_oname1 : {}", iterNum);
            }
            for(int i = 0; i < iterNum; i++){
                lessonFile.setLessonFileSname(file_sname.get(i));
                lessonFile.setLessonFileOname(file_oname.get(i));
                lessonFileRepository.save(lessonFile);
            }

//            //
//            for(int i = 0; i < file_oname.size(); i++){
//                String temp_file_oname = file_oname.get(i);
//                String temp_file_sname = file_sname.get(i);
//                lessonFileRepository.save(LessonFile.builder()
//                        .lessonFileSname(temp_file_sname)
//                        .lessonFileOname(temp_file_oname)
//                        .lesson(lesson)
//                        .build()
//                );
//            }

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
            lesson.setLessonAddr(lessonRequestDto.getLesson_addr());
            lesson.setLessonLongitude(lessonRequestDto.getLesson_longitude());
            lesson.setLessonLatitude(lessonRequestDto.getLesson_latitude());
            lesson.setLessonCategory(lessonRequestDto.getLesson_category());
            lesson.setLessonURL(lessonRequestDto.getLesson_URL());
//            lesson.setLessonContent(lessonRequestDto.getLesson_content());

            // 이것도 수정
            String requestOname = lessonRequestDto.getLesson_oname();
            String requestSname = lessonRequestDto.getLesson_sname();
            // 기존의 썸네일 이미지 삭제
            if(requestOname == null || requestOname.trim().isEmpty() || requestSname == null || requestSname.trim().isEmpty()){
                imageService.deleteS3(lesson.getLessonOname());
            }else{
                // 썸네일이 바뀐 경우
                if(!requestOname.equals(lesson.getLessonOname())){
                    // 기존 이미지 삭제
                    if(lesson.getLessonOname() != null && lesson.getLessonSname() != null){
                        imageService.deleteS3(lesson.getLessonOname());
                    }
                    // 새로운 이미지 저장
                    String newLessonOname = "lessonThumbnail/" + requestOname.split("/")[2];
                    imageService.copyS3(requestOname, newLessonOname);

                    requestOname = newLessonOname;
                    requestSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newLessonOname;

                }
            }
            // DB에 변경사항 반영
            lesson.setLessonSname(requestSname);
            lesson.setLessonOname(requestOname);

            // file 수정
            List<LessonFile> lessonFileList = lessonFileRepository.findLessonFilesByLesson_LessonNo(lesson_no);
            // requestFIleOname : temp/lesson/파일명
            List<String> requestFileOname = lessonRequestDto.getLesson_file_oname(); // 이게 null일 수 도 있음
            List<String> requestFileSname = lessonRequestDto.getLesson_file_sname();

            /*
                1. 현재 파일에 없고, 새로운 파일에 있는 경우 => 새로 만들어줌
                2. 현재 파일이 있고, 새로운 파일에도 있는 경우 => 그냥 둠
                3. 현재 파일에 있고, 새로운 파일에 없는 경우 => 삭제
             */
            // 현재 db에 자장된 파일 이름
            Set<String> curFileOname = lessonFileList.stream()
                    .map(LessonFile::getRealOname)
                    .collect(Collectors.toSet());

            if(requestFileOname != null && requestOname != null && (requestFileOname.size() != requestFileSname.size())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "fileOname과 fileSname의 크기 다릅니다."));
            }

            // requestFileOname : temp/lesson/파일명
            // temp/
            String content = lessonRequestDto.getLesson_content();
            int iterNum = 0;
            if(requestFileOname == null){
                iterNum = 0;
            }else{
                iterNum = requestFileOname.size();
            }
            for(int i = 0; i < iterNum; i++){
                // 기존에 없는 파일이 추가된 경우 // curFileOname :  lesson/파일명
                int idx = requestFileOname.get(i).split("/").length - 1;
                String fileOnameWoDir = requestFileOname.get(i).split("/")[idx]; // fileOname without dir
                // 1. 현재 파일에 없고, 새로운 파일에 있는 경우 => 새로 만들어줌 : content도 수정해줘야함
                if(!curFileOname.contains(fileOnameWoDir)){
                    log.info("1. 현재 파일에 없고, 새로운 파일에 있는 경우 => 새로운 파일 추가");
                    String newFileOname = "lesson/" + fileOnameWoDir;
                    imageService.copyS3(requestFileOname.get(i), newFileOname);

                    String newFileSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newFileOname;

                    if(content.contains(requestFileSname.get(i))){
                        log.info("lessonUpdate content update");
                        content = content.replace(requestFileSname.get(i), newFileSname);
                    }
                    LessonFile lessonFile = new LessonFile();
                    lessonFile.setLessonFileSname(newFileSname);
                    lessonFile.setLessonFileOname(newFileOname);
                    lessonFile.setLesson(lesson);
                    lessonFileRepository.save(lessonFile);
                }else{ // 2. 현재 파일에도 있고 새로운 파일에도 있는 경우 => 그대로 둠
                    log.info("2. 현재 파일에도 있고 새로운 파일에도 있는 경우 => 그대로 둠");
                    curFileOname.remove(fileOnameWoDir); // set에서 제거해줌
                }
            }
            lesson.setLessonContent(content);
            // 3. 현재 파일에 있고, 새로운 파일에 없는 경우 => 삭제
            for(String iter : curFileOname){
                log.info("3. 현재 파일에 있고, 새로운 파일에 없는 경우 => 삭제 : {}", iter);
                // S3 삭제
                String fileOname = "lesson/" + iter;
                imageService.deleteS3(fileOname);

                // DB에서 삭제 // DB에는 oname : lesson/파일명
                if(!lessonFileRepository.existsLessonFileByLessonFileOname(fileOname)){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISA", "삭제할 fileOname에 맞는 파일명이 DB에 존재하지 않습니다."));
                }
                lessonFileRepository.deleteLessonFileByLessonFileOname(fileOname);
            }
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

    // 특정 클래스 조회(로그인 + 비로그인) // 즐겨찾기 목록
    @Override
    public ResponseEntity<? super FindOneResponseDto> findOneResponseDto(Boolean loginStatus, Long lesson_no, String userId){
//    public void findOneResponseDto(Long lesson_no){
        try{
            User user = userRepository.findByUserId(userId);
            // 해당하는 lesson이 없는 경우
            if(!lessonJpaDataRepository.existsLessonByLessonNo(lesson_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA","해당하는 lesson_no을 가진 클래스가 없습니다."));
            }
            Lesson lesson = lessonJpaDataRepository.findLessonByLessonNo(lesson_no);
            // file_oname, file_sname 설정
            List<LessonFile> lessonFileList = lessonFileRepository.findLessonFilesByLesson_LessonNo(lesson_no);
            log.info("lessonFileSize : {}", lessonFileList.size());
            LessonResponseDto lessonResponseDto = new LessonResponseDto(lesson, lessonFileList);
            if(loginStatus){
                Boolean like_status = muziLessonLikeRepository.existsByUser_UserNoAndLesson_LessonNo(user.getUserNo(), lesson_no);
                lessonResponseDto.setLesson_like_status(like_status);
            }else{
                lessonResponseDto.setLesson_like_status(false);
            }
            Long likes_no = muziLessonLikeRepository.countLessonLikeByLesson_LessonNo(lesson_no);
            lessonResponseDto.setLesson_like_cnt(likes_no);

            return ResponseEntity.status(HttpStatus.OK).body(new FindOneResponseDto(lessonResponseDto));
        }catch (Exception e){
            log.info("LessonService findOneResponseDto Exception : {}", e.getMessage());
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
            // 구매 정보 저장 // 구매 정보가 있는지 여부를 먼저 확인해야함
            if(payRepository.existsPayByLesson_LessonNoAndUser(lesson_no, user)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "이미 구매한 클래스입니다."));
            }
            Pay pay = new Pay();
            pay.setUser(user);
            // lesson // clinet에게 전송할 때도 사용
            Lesson lesson = lessonsRepository.findByLesson_no(lesson_no);
            if(lesson.getLessonDeleteStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당 클래스는 삭제된 클래스 입니다."));
            }
            pay.setLesson(lesson);
            pay.setPayDate(LocalDateTime.now());
            // pay_price
            pay.setPayPrice(lesson.getLessonPrice());
            // pay_code
            Long cntPay = payRepository.countAllByPayNoGreaterThan(-1L);
            String pay_code = LocalDate.now().toString() + "_" + cntPay; // ex) 2019-09-19_lesson_no;
            pay.setPayCode(pay_code);
            System.out.println(pay.getLesson().getLessonNo());

//            payJpaRepository.PayCreate(pay);
            payRepository.save(pay);

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
            reviewEnableRepository.save(reviewEnable);

            return ResponseEntity.status(HttpStatus.OK).body(new PaymentInfoResponseDto(payInfo));

        }catch (Exception e){
            log.info("LessonService buyLesson Exception : {}", e.getMessage());
            log.info("getStackTrace()[0] : {}", e.getStackTrace()[0]);
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
    // 인기 클래스 조회
    @Override
    public ResponseEntity<? super FindAllResponseDto> getMostLikedLesson(){
        try {
            List<LessonMostLikeDto> lessonMostLikeDtoList = lessonRepository.getMostLiked().stream().map(LessonMostLikeDto::from).toList();
            return ResponseEntity.status(HttpStatus.OK).body(lessonMostLikeDtoList);
        } catch (Exception e){
            log.info("LessonService getMostLikedLesson error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 카테고리별 조회
    @Override
    public ResponseEntity<? super LessonCategoryResponseDto> getLessonsCategory(Boolean loginStatus, String koreaName, String userId, Pageable pageable){
        try {
            Category category = Category.parsing(koreaName);
            List<LessonShortDto> lessonShortDtoList = lessonRepository.findAllByLessonCategory(category, pageable)
                    .map(LessonShortDto::from).getContent();

            for(LessonShortDto iter : lessonShortDtoList){
                Long lesson_no = iter.getLesson_no();
                iter.setLesson_like_cnt(lessonLikeRepository.countLessonLikeByLesson_LessonNo(lesson_no));
                if(loginStatus){
                    iter.setLesson_like_status(lessonLikeRepository.existsLessonLikeByLesson_LessonNoAndUser_UserId(lesson_no, userId));
                }else{
                    iter.setLesson_like_status(false);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new LessonCategoryResponseDto("SU", "success", lessonShortDtoList));
        } catch (Exception e){
            log.info("LessonService getLessonsCategory error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
}
