package com.flowerbowl.web.admin.adminDTO;

import com.flowerbowl.web.commonDTO.ResponseDto;
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
