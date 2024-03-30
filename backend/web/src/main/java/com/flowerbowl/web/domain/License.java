package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_no")
    private Long licenseNo;

    @Column(name = "license_status") // 기본값을 넣은건데 제외해도 될 것 같아요
    private Boolean licenseStatus;

    @Column(name = "license_date")
    private LocalDateTime licenseDate;

    @Column(name = "license_file_oname")
    private String licenseFileOname;

    @Column(name = "license_file_sname")
    private String licenseFileSname;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

}
