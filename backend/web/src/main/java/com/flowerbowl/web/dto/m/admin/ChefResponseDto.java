package com.flowerbowl.web.dto.m.admin;

import com.flowerbowl.web.dto.m.ResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChefResponseDto extends ResponseDto{
    public ChefResponseDto(){
        super();
        this.code = "SU";
        this.message = "success";
    }
    private String code;
    private String message;
    private List<ChefCandidiateDto> candidiate = new ArrayList<>();
}
