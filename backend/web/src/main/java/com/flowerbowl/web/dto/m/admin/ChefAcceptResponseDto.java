package com.flowerbowl.web.dto.m.admin;

import com.flowerbowl.web.dto.m.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChefAcceptResponseDto extends ResponseDto{
    private String code;
    private String message;
}
