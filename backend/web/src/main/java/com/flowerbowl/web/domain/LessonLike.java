package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class LessonLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_like_no")
    private Long lessonLikeNo;

    @ManyToOne // lessonLike(Many) to user(one)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_no")
    private Lesson lesson;
}

