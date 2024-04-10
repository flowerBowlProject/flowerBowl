package com.flowerbowl.web.dto.m.lesson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoResponseDto {
    private Long code;
    private String message;
    private PayInfo payinfo;

    public PaymentInfoResponseDto(PayInfo payinfo){
        code = 200L;
        message = "SU";
        this.payinfo = payinfo;
    }
    public PaymentInfoResponseDto(){
        code = 400L;
        message = "Bad Request";
    }
}
