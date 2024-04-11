package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.community.request.CrCommunityReqDto;
import com.flowerbowl.web.dto.community.request.UpCommunityReqDto;
import com.flowerbowl.web.dto.community.response.CommunityResponseDto;
import com.flowerbowl.web.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("")
    private ResponseEntity<? extends CommunityResponseDto> createCommunity(@RequestBody CrCommunityReqDto request) throws Exception {
        return communityService.createCommunity(request);
    }

    @PutMapping("{community_no}")
    private ResponseEntity<? extends CommunityResponseDto> updateCommunity(@RequestBody UpCommunityReqDto request, @PathVariable Long community_no) throws Exception {
        return communityService.updateCommunity(request, community_no);
    }

}
