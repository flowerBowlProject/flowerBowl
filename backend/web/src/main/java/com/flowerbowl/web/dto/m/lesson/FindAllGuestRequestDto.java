package com.flowerbowl.web.dto.m.lesson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllGuestRequestDto {
    private Long page;
    private Long size;
}
