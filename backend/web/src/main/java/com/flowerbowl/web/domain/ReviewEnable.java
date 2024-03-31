package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class ReviewEnable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_enable_no")
    private Long reviewEnableNo;

    @Column(name = "review_enable")
    @ColumnDefault("false")
    private Boolean reviewEnable;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_no")
    private Lesson lesson;

}

