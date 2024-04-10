package com.flowerbowl.web.dto.m.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChefCandidiateDto{
    private Long license_no;
    private Boolean license_status;
    private LocalDateTime license_date;
    private String license_sname;
    private Long user_no;
}
