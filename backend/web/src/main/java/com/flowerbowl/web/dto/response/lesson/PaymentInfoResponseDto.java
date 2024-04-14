package com.flowerbowl.web.dto.response.lesson;

import com.flowerbowl.web.dto.object.lesson.PayInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoResponseDto extends ResponseDto {
    private String code;
    private String message;
    private PayInfo payinfo;

    public PaymentInfoResponseDto(PayInfo payinfo){
        code = "SU";
        message = "success";
        this.payinfo = payinfo;
    }
    public PaymentInfoResponseDto(){
        code = "FA";
        message = "Bad Request";
    }
}
