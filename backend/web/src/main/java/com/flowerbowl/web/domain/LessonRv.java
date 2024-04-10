package com.flowerbowl.web.domain;

import com.flowerbowl.web.dto.request.review.InsertReviewRequestDto;
import com.flowerbowl.web.dto.request.review.PatchReviewRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class LessonRv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_rv_no")
    private Long lessonRvNo;

    @Column(name = "lesson_rv_content")
    private String lessonRvContent;

    @Column(name = "lesson_rv_date")
    private LocalDate lessonRvDate;

    @Column(name = "lesson_rv_score")
    private Integer lessonRvScore;

    @ManyToOne // LessonRv(Many) to user(one)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne // LessonRv(many) to Lesson(one)
    @JoinColumn(name = "lesson_no") //
    private Lesson lesson;


    public LessonRv(InsertReviewRequestDto dto, User user, Lesson lesson) {
        this.lessonRvContent = dto.getReview_content();
        this.lessonRvScore = dto.getReview_score();
        this.lessonRvDate = LocalDate.now();
        this.user = user;
        this.lesson = lesson;
    }

}

