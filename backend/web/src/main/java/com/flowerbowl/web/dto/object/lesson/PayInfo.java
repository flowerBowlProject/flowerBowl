package com.flowerbowl.web.dto.object.lesson;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
public class PayInfo {
    private String pg; // pg_code + . + pg_id
//    private String pg_code;
//    private String pg_id;
    private String pay_method; // card
    private String merchant_uid; // 주문번호
    private String name; // 구매제품 이름
    private Long amount; // 가격
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
//    merchant_uid: "ORD20181131-0110997",   // 주문번호
//    name: "쿠킹클래스", // 카카오페이에 나타나는 구매제품 이름
//    amount: 1,       // 가격                  // 숫자 타입
//    buyer_email: "gildong@gmail.com",
//    // buyer_name: "홍길동",
//    buyer_tel: "010-4242-4242",
//    private String store_id; // 이거는 따로 빼야함
//
//    private String order_no;
//    private String user_nickname;
//    private String user_phone_number;
//    private String user_email;

    public void kakaopay(){
        pg = "kakaopay.TC0ONETIME";
        pay_method = "card";
//        pg_code = "kakaopay";
//        pg_id = "TC0ONETIME";
//        store_id = "imp75223852"; // imp75223852
//      // store-2f98d4ee-5d6a-48ff-8d04-d07b1ec8bf16
    }
}
