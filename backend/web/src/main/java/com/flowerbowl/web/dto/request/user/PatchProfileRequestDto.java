package com.flowerbowl.web.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchProfileRequestDto {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,10}$", message = "특수문자는 사용하지 못하며, 2~10자리 이어야 합니다.")
    private String new_nickname;

    @Pattern(regexp = "^[0-9]{11}$", message = "-를 제외하고 입력해주세요")
    private String new_phone;

    @Email
    private String new_email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+])[a-zA-Z0-9!@#$%^&*()_+]{8,15}$"
            , message = "대소문자(a~z, A~Z), 숫자(0~9), 특수 문자(!@#$%^&*()_+)를 한 번씩 포함, 8~15자로 작성해 주세요.")
    private String new_pw;

    private String user_password;
    private String user_file_sname;
    private String user_file_oname;
}
