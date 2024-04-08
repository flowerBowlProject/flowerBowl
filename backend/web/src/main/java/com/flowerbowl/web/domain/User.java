package com.flowerbowl.web.domain;

import com.flowerbowl.web.dto.request.auth.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_createdate")
    private LocalDate userCreateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role userRole;

    @Column(name = "user_file_sname")
    private String userFileSname;

    @Column(name = "user_file_oname")
    private String userFileOname;

    @Column(name = "user_wd_status")
    @ColumnDefault("false")
    private Boolean userWdStatus;

//    @Column(name = "user_type")
//    private String userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RecipeLike> recipeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LessonLike> lessonLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LessonRv> lessonRvs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewEnable> reviewEnables = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Pay> pays = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Community> communities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<License> licenses = new ArrayList<>();

    public User(SignUpRequestDto dto) {
        this.userId = dto.getUser_id();
        this.userPw = dto.getUser_password();
        this.userEmail = dto.getUser_email();
        this.userNickname = dto.getUser_nickname();
        this.userPhone = dto.getUser_phone();
//        this.userType = "web";
        this.userCreateDate = LocalDate.now();
        this.userRole = Role.ROLE_CHEF;
        this.userWdStatus = false; // @ColumnDefault("false")이 안 먹음
    }


    public void setUserFileSname(String userFileSname) {
        this.userFileSname = userFileSname;
    }

    public void setUserFileOname(String userFileOname) {
        this.userFileOname = userFileOname;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
