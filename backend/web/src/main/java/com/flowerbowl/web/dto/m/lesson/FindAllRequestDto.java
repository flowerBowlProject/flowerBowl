package com.flowerbowl.web.dto.m.lesson;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FindAllRequestDto {
    private Long page;
    private Long size;
}
