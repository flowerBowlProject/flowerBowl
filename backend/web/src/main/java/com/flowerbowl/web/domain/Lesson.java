package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_no")
    private Long lessonNo;

    @Column(name = "lesson_title")
    private String lessonTitle;

    @Column(name = "lesson_content", columnDefinition = "TEXT")
    private String lessonContent;

    @Column(name = "lesson_price")
    private String lessonPrice;

    @Column(name = "lesson_sname")
    private String lessonSname;

    @Column(name = "lesson_oname")
    private String lessonOname;

    @Column(name = "lesson_addr")
    private String lessonAddr;

    @Column(name = "lesson_start")
    private LocalDateTime lessonStart;

    @Column(name = "lesson_end")
    private LocalDateTime lessonEnd;

    @Column(name = "lesson_category")
    @Enumerated(EnumType.STRING)
    private Category lessonCategory;

    @Column(name = "lesson_date") // 클래스 작성날짜
    private LocalDate lessonDate;

    @Column(name = "lesson_URL")
    private String lessonURL;

    @Column(name = "lesson_writer")
    private String lessonWriter;

    @Column(name = "lesson_delete_status")
    @ColumnDefault("false")
    private Boolean lessonDeleteStatus;

    @Column(name = "lesson_views")
    private Integer lessonViews;

    @Column(name = "lesson_longitude") // 경도
    private Double lessonLongitude;

    @Column(name = "lesson_latitude") // 위도
    private Double lessonLatitude;

    @ManyToOne // many(lesson) to user(강좌개설한 사람)
    @JoinColumn(name = "user_no")
    private User user;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Pay> pays = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<LessonFile> lessonFiles = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<LessonLike> lessonLikes = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<LessonRv> lessonRvs = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<ReviewEnable> reviewEnables = new ArrayList<>();

}

