package com.flowerbowl.web.dto.request.mypage;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertLicenseRequestDto {

    @NotBlank
    private String chef_oname;

    @NotBlank
    private String chef_sname;
}
