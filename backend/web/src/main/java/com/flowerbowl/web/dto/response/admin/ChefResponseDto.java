package com.flowerbowl.web.dto.response.admin;

import com.flowerbowl.web.dto.object.admin.ChefCandidiateDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChefResponseDto extends ResponseDto {
    public ChefResponseDto(){
        super();
        this.code = "SU";
        this.message = "success";
    }
    private String code;
    private String message;
    private List<ChefCandidiateDto> candidiate = new ArrayList<>()
}
