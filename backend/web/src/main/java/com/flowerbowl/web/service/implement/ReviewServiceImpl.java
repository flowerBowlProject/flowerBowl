package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.Lesson;
import com.flowerbowl.web.domain.LessonRv;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.object.mypage.AvailableReviews;
import com.flowerbowl.web.dto.object.mypage.WrittenReviews;
import com.flowerbowl.web.dto.object.review.GetReviewDto;
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

            List<Object[]> dbResult = userRepository.findAvailableReviewListByUserId(userId);
            if (dbResult == null) AvailableReviewsResponseDto.noExistLesson();


            for (Object[] posts : dbResult) {
                AvailableReviews reviewList = new AvailableReviews();
                reviewList.setLesson_no((Long) posts[0]);
                reviewList.setLesson_title((String) posts[1]);
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

            LessonRv lessonRv = reviewRepository.findByLessonRvNo(reviewNo);
            if (!userId.equals(lessonRv.getUser().getUserId())) return PatchReviewResponseDto.notMatchUser();

            lessonRv.setLessonRvContent(dto.getReview_content());
            lessonRv.setLessonRvScore(dto.getReview_score());
            reviewRepository.save(lessonRv);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchReviewResponseDto.success();
    }

    @Override
    public ResponseEntity<? super DeleteReviewResponseDto> reviewDelete(String userId, Long reviewNo) {

        try {

            LessonRv lessonRv = reviewRepository.findByLessonRvNo(reviewNo);
            if (lessonRv == null) return DeleteReviewResponseDto.notExistNum();

            if (!userId.equals(lessonRv.getUser().getUserId())) return DeleteReviewResponseDto.notMatchUser();

            reviewRepository.deleteById(reviewNo);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeleteReviewResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetReviewResponseDto> getReview(String userId, Long reviewNo) {

        GetReviewDto getReviewDto = new GetReviewDto();

        try {

            List<Object[]> review = reviewRepository.findReviewByReviewNo(reviewNo);
            if (review == null) return GetReviewResponseDto.noExistReview();

            LessonRv lessonRv = reviewRepository.findByLessonRvNo(reviewNo);
            if (!userId.equals(lessonRv.getUser().getUserId())) return GetReviewResponseDto.noMatchUser();

            for (Object[] posts : review) {
                getReviewDto.setReview_score((Integer) posts[0]);
                getReviewDto.setReview_content((String) posts[1]);
                getReviewDto.setLesson_title((String) posts[2]);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetReviewResponseDto.success(getReviewDto);
    }

}
