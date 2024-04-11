package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.Lesson;
import com.flowerbowl.web.domain.LessonRv;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.object.mypage.AvailableReviews;
import com.flowerbowl.web.dto.object.mypage.WrittenReviews;
import com.flowerbowl.web.dto.request.review.InsertReviewRequestDto;
import com.flowerbowl.web.dto.request.review.PatchReviewRequestDto;
import com.flowerbowl.web.dto.response.ResponseDto;
import com.flowerbowl.web.dto.response.review.*;
import com.flowerbowl.web.repository.LessonRepository;
import com.flowerbowl.web.repository.ReviewEnableRepository;
import com.flowerbowl.web.repository.ReviewRepository;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final ReviewEnableRepository reviewEnableRepository;


    @Override
    public ResponseEntity<? super InsertReviewResponseDto> reviewInsert(InsertReviewRequestDto dto, String userId) {

        try {

            User user = userRepository.findByUserId(userId);
            Lesson lesson = lessonRepository.findByLessonNo(dto.getLesson_no());

            LessonRv lessonRv = new LessonRv(dto, user, lesson);
            lessonRv.getLesson().getLessonNo();
            reviewRepository.save(lessonRv);

            Long userNo = user.getUserNo();
            Long lessonNo = lesson.getLessonNo();
            reviewEnableRepository.updateReviewEnableToFalse(userNo, lessonNo);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return InsertReviewResponseDto.success();
    }

    @Override
    public ResponseEntity<? super AvailableReviewsResponseDto> availableReviewList(String userId) {

        List<AvailableReviews> availableReviews = new ArrayList<>();

        try {

            List<Lesson> posts = userRepository.findAvailableReviewListByUserId(userId);
            if (posts == null) {
                AvailableReviewsResponseDto.noExistLesson();
            }

            for (Lesson lesson : posts) {
                AvailableReviews reviewList = new AvailableReviews();
                reviewList.setLesson_no(lesson.getLessonNo());
                reviewList.setLesson_title(lesson.getLessonTitle());
                availableReviews.add(reviewList);
            }


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return AvailableReviewsResponseDto.success(availableReviews);
    }

    /**
     * List<Object[]>를 반환하는 list은 view 테이블 만들면 더 좋을 듯
     */
    @Override
    public ResponseEntity<? super WrittenReviewsResponseDto> writtenReviews(String userId) {

        List<WrittenReviews> WrittenReviews = new ArrayList<>();

        try {

            List<Object[]> reviews = userRepository.findReviewsByUserId(userId);
            if (reviews == null) WrittenReviewsResponseDto.noExistReview();

            for (Object[] review : reviews) {
                WrittenReviews writtenReviews = new WrittenReviews();
                writtenReviews.setReview_score((Integer) review[0]);
                writtenReviews.setReview_date((String) review[1]);
                writtenReviews.setReview_no((Long) review[2]);
                writtenReviews.setLesson_title((String) review[3]);
                writtenReviews.setLesson_writer((String) review[4]);
                WrittenReviews.add(writtenReviews);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return WrittenReviewsResponseDto.success(WrittenReviews);
    }


    @Override
    public ResponseEntity<? super PatchReviewResponseDto> reviewUpdate(
            PatchReviewRequestDto dto, String userId,
            Long reviewNo) {

        try {
            String reviewContent = dto.getReview_content();
            Integer reviewScore = dto.getReview_score();

            reviewRepository.updateLessonReview(reviewContent, reviewScore, reviewNo);
//            reviewRepository.save(lessonRv);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchReviewResponseDto.success();
    }

    @Override
    public ResponseEntity<? super DeleteReviewResponseDto> reviewDelete(Long reviewNo) {

        try {

            Optional<LessonRv> lessonRv = reviewRepository.findById(reviewNo);
            if (lessonRv.isEmpty()) {return DeleteReviewResponseDto.notExistNum();}

            reviewRepository.deleteById(reviewNo);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeleteReviewResponseDto.success();
    }

}
