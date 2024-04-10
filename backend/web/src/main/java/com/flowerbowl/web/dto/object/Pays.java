package com.flowerbowl.web.dto.object;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Pays {

    private String pay_date;
    private String pay_price;
    private String lesson_title;
    private String lesson_writer;
}
