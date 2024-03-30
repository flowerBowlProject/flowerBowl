package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

}
