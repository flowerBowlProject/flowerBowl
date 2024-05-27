package com.flowerbowl.web.dto.request.lesson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayErrorRequestDto {
    private Boolean pay_success;
    private String pay_error_code;
    private String pay_error_msg;
    private String merchant_uid;
}
