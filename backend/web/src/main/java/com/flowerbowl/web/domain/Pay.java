package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_no")
    private Long payNo;

    @Column(name = "pay_code")
    private String payCode;

    @Column(name = "pay_date")
    private LocalDateTime payDate;

    @Column(name = "pay_price")
    private String payPrice;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_no")
    private Lesson lesson;

}
