package com.flowerbowl.web.dto.object.admin;

import com.flowerbowl.web.domain.License;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChefCandidiateDto{
    private Long license_no;
    private Boolean license_status; // 왜 있는거?
    private LocalDateTime license_date;
    private String license_sname;
    private String license_oname;
    private Long user_no;
    private String user_name;

    public static ChefCandidiateDto from(License license){
        return ChefCandidiateDto.builder()
                .license_no(license.getLicenseNo())
                .license_status(license.getLicenseStatus())
                .license_date(license.getLicenseDate())
                .license_sname(license.getLicenseFileSname())
                .license_oname(license.getLicenseFileOname())
                .user_no(license.getUser().getUserNo())
                .user_name(license.getUser().getUserNickname())
                .build();
    }
}
