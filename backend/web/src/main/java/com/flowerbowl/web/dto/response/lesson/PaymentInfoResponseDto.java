package com.flowerbowl.web.dto.response.lesson;

import com.flowerbowl.web.dto.object.lesson.PayInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class PaymentInfoResponseDto extends ResponseDto {
    private String code;
    private String message;

    @Value("${payment.store_id}")
    private String store_id; // imp75223852 따로 처리해야 하므로 밖으로 뻄
    private PayInfo request_pay;

    public PaymentInfoResponseDto(PayInfo payinfo){
        code = "SU";
        message = "success";
        this.request_pay = payinfo;
        store_id = "imp75223852"; // 이거 수정 value 받는 걸로
    }
//    public PaymentInfoResponseDto(){
//        code = "FA";
//        message = "Bad Request";
//    }
}
