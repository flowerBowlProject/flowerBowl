package com.flowerbowl.web.dto.response.user;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.Role;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.response.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetUserInfoResponseDTO extends ResponseDTO {

    private String user_nickname;
    private String user_email;
    private String user_phone;
    private Role user_role;
    private String user_file_sname;
    private String user_file_oname;

    private GetUserInfoResponseDTO(User user) {
        super();
        this.user_nickname = user.getUserNickname();
        this.user_email = user.getUserEmail();
        this.user_phone = user.getUserPhone();
        this.user_role = user.getUserRole();
        this.user_file_sname = user.getUserFileSname();
        this.user_file_oname = user.getUserFileOname();
    }

    public static ResponseEntity<GetUserInfoResponseDTO> success(User user){
        GetUserInfoResponseDTO body = new GetUserInfoResponseDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDTO> notExistUser(){
        ResponseDTO body = new ResponseDTO(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }


}
