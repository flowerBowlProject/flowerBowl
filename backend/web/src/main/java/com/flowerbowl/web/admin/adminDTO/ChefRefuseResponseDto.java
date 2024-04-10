package com.flowerbowl.web.admin.adminDTO;

import com.flowerbowl.web.commonDTO.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChefRefuseResponseDto extends ResponseDto{
    private String code;
    private String message;
}
