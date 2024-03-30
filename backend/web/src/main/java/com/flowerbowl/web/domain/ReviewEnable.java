package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ReviewEnable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_enable_no")
    private Long reviewEnableNo;

    @Column(name = "review_enable") // table에 변수명 추가해 줘야함
    private Boolean reviewEnable;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_no")
    private Lesson lesson;

}

