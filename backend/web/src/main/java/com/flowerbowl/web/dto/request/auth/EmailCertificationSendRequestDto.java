package com.flowerbowl.web.dto.request.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailCertificationSendRequestDto {

    private String user_id;
    @NotBlank
    private String user_email;
}
