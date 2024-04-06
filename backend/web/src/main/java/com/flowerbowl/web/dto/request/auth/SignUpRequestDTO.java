package com.flowerbowl.web.dto.request.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignUpRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{8,15}$", message = "소문자(a~z), 숫자(0~9)만 사용 가능, 8~15자로 작성해 주세요.")
    private String user_id;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+])[a-zA-Z0-9!@#$%^&*()_+]{8,15}$"
            , message = "대소문자(a~z, A~Z), 숫자(0~9), 특수 문자(!@#$%^&*()_+)를 한 번씩 포함, 8~15자로 작성해 주세요.")
    private String user_password;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,10}$", message = "특수문자는 사용하지 못하며, 2~10자리 이어야 합니다.")
    private String user_nickname;

    @NotBlank
    @Email
    private String user_email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$", message = "-를 제외하고 입력해주세요")
    private String user_phone;
}
