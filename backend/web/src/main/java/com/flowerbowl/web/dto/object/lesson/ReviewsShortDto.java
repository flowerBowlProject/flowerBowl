package com.flowerbowl.web.dto.object.lesson;

import com.flowerbowl.web.domain.LessonRv;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewsShortDto {
    private Long review_no;
    private String review_content;
    private LocalDate review_date;
    private int review_score;
    private Long user_no;

    public static ReviewsShortDto from(LessonRv lessonRv){
        return ReviewsShortDto.builder()
                .review_no(lessonRv.getLessonRvNo())
                .review_content(lessonRv.getLessonRvContent())
                .review_date(lessonRv.getLessonRvDate())
                .review_score(lessonRv.getLessonRvScore())
                .user_no(lessonRv.getUser().getUserNo())
                .build();
    }
}
