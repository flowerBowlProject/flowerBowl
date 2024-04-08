package com.flowerbowl.web.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindUserIdRequestDto {

    private String user_nickname;

    @Email
    @NotBlank
    private String user_email;

}
