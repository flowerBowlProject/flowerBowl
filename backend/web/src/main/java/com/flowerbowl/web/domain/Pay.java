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

    @Column(name = "pay_date") // 결제 날짜 같은 경우는 시간도 필요할거 같아서 localdatetime으로 했습니다
    private LocalDateTime payDate;

    @Column(name = "pay_price") // 일단 string인데 자료형 변경 물어봐야 됨
    private String payPrice;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_no")
    private Lesson lesson;

}
