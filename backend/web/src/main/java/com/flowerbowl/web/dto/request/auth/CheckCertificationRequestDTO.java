package com.flowerbowl.web.dto.request.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckCertificationRequestDTO {

    private String user_id;

    @NotBlank
    @Email
    private String user_email;

    @NotBlank
    private String certification_num;

}
