package com.flowerbowl.web.domain;

import com.flowerbowl.web.dto.request.mypage.InsertLicenseRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_no")
    private Long licenseNo;

    @Column(name = "license_status")
    @ColumnDefault("false")
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

    public License(InsertLicenseRequestDto dto, User user) {
        this.licenseStatus = false;
        this.licenseDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.licenseFileOname = dto.getChef_oname();
        this.licenseFileSname = dto.getChef_sname();
        this.user = user;
    }
}
