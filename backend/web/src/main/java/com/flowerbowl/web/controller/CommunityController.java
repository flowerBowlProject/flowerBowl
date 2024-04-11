package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.community.request.CrCommunityReqDto;
import com.flowerbowl.web.dto.community.response.CommunityResponseDto;
import com.flowerbowl.web.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("")
    private ResponseEntity<? extends CommunityResponseDto> createCommunity(@RequestBody CrCommunityReqDto request) throws Exception {
        return communityService.createCommunity(request);
    }

}
