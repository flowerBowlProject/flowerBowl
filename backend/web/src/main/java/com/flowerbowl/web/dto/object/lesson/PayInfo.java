package com.flowerbowl.web.dto.object.lesson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayInfo {
    private String pg_code;
    private String pg_id;
    private String store_id;

    private String order_no;
    private String user_nickname;
    private String user_phone_number;
    private String user_email;

    public void kakaopay(){
        pg_code = "kakaopay";
        pg_id = "TC0ONETIME";
        store_id = "store-2f98d4ee-5d6a-48ff-8d04-d07b1ec8bf16";
    }
}
