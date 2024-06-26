package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.request.community.CrCommunityReqDto;
import com.flowerbowl.web.dto.request.community.UpCommunityReqDto;
import com.flowerbowl.web.dto.response.community.CommunityResponseDto;
import com.flowerbowl.web.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("")
    private ResponseEntity<? extends CommunityResponseDto> createCommunity(@RequestBody CrCommunityReqDto request, @AuthenticationPrincipal String userId) throws Exception {
        return communityService.createCommunity(request, userId);
    }

    @PutMapping("{community_no}")
    private ResponseEntity<? extends CommunityResponseDto> updateCommunity(@RequestBody UpCommunityReqDto request, @PathVariable Long community_no, @AuthenticationPrincipal String userId) throws Exception {
        return communityService.updateCommunity(request, community_no, userId);
    }

    @DeleteMapping("{community_no}")
    private ResponseEntity<? extends CommunityResponseDto> deleteCommunity(@PathVariable Long community_no, @AuthenticationPrincipal String userId) throws Exception {
        return communityService.deleteCommunity(community_no, userId);
    }

    @GetMapping("/list")
    private ResponseEntity<? extends CommunityResponseDto> getAllCommunities(@RequestParam int page, @RequestParam int size) throws Exception {
        return communityService.getAllCommunities(page - 1, size);
    }

    @GetMapping("/detail/{community_no}")
    private ResponseEntity<? extends CommunityResponseDto> getCommunity(@PathVariable Long community_no) throws Exception {
        return communityService.getCommunity(community_no);
    }

}
