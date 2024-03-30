package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class LessonFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_file_no")
    private Long lessonFileNo;

    @Column(name = "lesson_file_oname")
    private String lessonFileOname;

    @Column(name = "lesson_file_sname")
    private String lessonFileSname;

    @ManyToOne // lessonFile(many) to lesson(one)
    @JoinColumn(name = "lesson_no") // Lesson table
    private Lesson lesson;

}

