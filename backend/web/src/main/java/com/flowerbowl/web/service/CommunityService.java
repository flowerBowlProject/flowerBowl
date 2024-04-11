package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.community.request.CrCommunityReqDto;
import com.flowerbowl.web.dto.community.response.CommunityResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommunityService {

    public ResponseEntity<? extends CommunityResponseDto> createCommunity(CrCommunityReqDto request) throws Exception;

}
