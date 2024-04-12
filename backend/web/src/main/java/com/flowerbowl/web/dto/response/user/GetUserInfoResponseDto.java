package com.flowerbowl.web.dto.response.user;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.Role;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetUserInfoResponseDto extends ResponseDto {

    private String user_nickname;
    private String user_email;
    private String user_phone;
    private Role user_role;
    private String user_file_sname;
    private String user_file_oname;

    private GetUserInfoResponseDto(User user) {
        super();
        this.user_nickname = user.getUserNickname();
        this.user_email = user.getUserEmail();
        this.user_phone = user.getUserPhone();
        this.user_role = user.getUserRole();
        this.user_file_sname = user.getUserFileSname();
        this.user_file_oname = user.getUserFileOname();
    }

    public static ResponseEntity<GetUserInfoResponseDto> success(User user){
        GetUserInfoResponseDto body = new GetUserInfoResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto body = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }


}
