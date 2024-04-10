package com.flowerbowl.web.search.searchDTO;

import com.flowerbowl.web.commonDTO.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SearchCommunityResponseDto extends ResponseDto {
    private String code;
    private String message;
    private List<CommunityShortDto> communityShortDtos = new ArrayList<>();
}
