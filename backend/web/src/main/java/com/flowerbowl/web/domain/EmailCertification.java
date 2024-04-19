package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class EmailCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "certification_num")
    private String certificationNum;

    public EmailCertification(String id, String email, String certificationNum) {
        this.id = id;
        this.email = email;
        this.certificationNum = certificationNum;
    }
}
