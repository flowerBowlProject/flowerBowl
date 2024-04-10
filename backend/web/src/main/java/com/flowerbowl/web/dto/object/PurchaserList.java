package com.flowerbowl.web.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PurchaserList {

    private String pay_date;
    private String lesson_title;
    private String user_nickname;
    private String user_phone;

}
