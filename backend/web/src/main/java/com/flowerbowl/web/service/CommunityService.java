package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.community.CrCommunityReqDto;
import com.flowerbowl.web.dto.request.community.UpCommunityReqDto;
import com.flowerbowl.web.dto.response.community.CommunityResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommunityService {

    public ResponseEntity<? extends CommunityResponseDto> createCommunity(CrCommunityReqDto request, String userId) throws Exception;

    public ResponseEntity<? extends CommunityResponseDto> updateCommunity(UpCommunityReqDto request, Long community_no, String userId) throws Exception;

    public ResponseEntity<? extends CommunityResponseDto> deleteCommunity(Long community_no, String userId) throws Exception;

    public ResponseEntity<? extends CommunityResponseDto> getAllCommunities(int page, int size) throws Exception;

    public ResponseEntity<? extends CommunityResponseDto> getCommunity(Long community_no) throws Exception;

}
