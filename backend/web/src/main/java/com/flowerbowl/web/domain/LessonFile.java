package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
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


    public String getRealOname(){
        int idx = lessonFileOname.split("/").length - 1;
        return lessonFileOname.split("/")[idx];
    }
}

