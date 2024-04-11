package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.community.request.CrCommunityReqDto;
import com.flowerbowl.web.dto.community.request.UpCommunityReqDto;
import com.flowerbowl.web.dto.community.response.CommunityResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommunityService {

    public ResponseEntity<? extends CommunityResponseDto> createCommunity(CrCommunityReqDto request) throws Exception;

    public ResponseEntity<? extends CommunityResponseDto> updateCommunity(UpCommunityReqDto request, Long community_no) throws Exception;

}
