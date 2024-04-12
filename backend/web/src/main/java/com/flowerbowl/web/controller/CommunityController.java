package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.request.community.CrCommunityReqDto;
import com.flowerbowl.web.dto.request.community.UpCommunityReqDto;
import com.flowerbowl.web.dto.response.community.CommunityResponseDto;
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

    @DeleteMapping("{community_no}")
    private ResponseEntity<? extends CommunityResponseDto> deleteCommunity(@PathVariable Long community_no) throws Exception {
        return communityService.deleteCommunity(community_no);
    }

    @GetMapping("/list")
    private ResponseEntity<? extends CommunityResponseDto> getAllCommunities(@RequestParam int page, @RequestParam int size) throws Exception {
        return communityService.getAllCommunities(page - 1, size);
    }

}
