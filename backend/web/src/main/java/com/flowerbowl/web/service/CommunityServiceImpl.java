package com.flowerbowl.web.service;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.community.CreateCommunityDto;
import com.flowerbowl.web.dto.community.CreateCommunityFileDto;
import com.flowerbowl.web.dto.community.request.CrCommunityReqDto;
import com.flowerbowl.web.dto.community.response.CommunityResponseDto;
import com.flowerbowl.web.dto.community.response.CrCommunityFaResDto;
import com.flowerbowl.web.dto.community.response.CrCommunitySuResDto;
import com.flowerbowl.web.handler.UserNotFoundException;
import com.flowerbowl.web.repository.CommunityFileRepository;
import com.flowerbowl.web.repository.CommunityRepository;
import com.flowerbowl.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityFileRepository communityFileRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? extends CommunityResponseDto> createCommunity(CrCommunityReqDto request) throws Exception {
        // 아직 token으로부터 사용자 정보를 얻어오는 부분이 없음
        try {
            Long userNo = 2L;

            User user = userRepository.findByUserNo(userNo).orElseThrow(UserNotFoundException::new);

            // request의 값으로 Community Dto 생성
            CreateCommunityDto createCommunityDto = new CreateCommunityDto(
                    request.getCommunityTitle(),
                    request.getCommunityContent(),
                    LocalDate.now(ZoneId.of("Asia/Seoul")),
                    "라이언",
                    0L,
                    user
            );

            // 생성된 community를 db에 저장 후 객체 반환
            Community community = communityRepository.save(createCommunityDto.toEntity());

            // request이 값으로 community file 생성 후 db에 저장
            CreateCommunityFileDto createCommunityFileDto = new CreateCommunityFileDto(request.getCommunityFileOname(), request.getCommunityFileSname(), community);
            communityFileRepository.save(createCommunityFileDto.toEntity());

            CrCommunitySuResDto responseBody = new CrCommunitySuResDto(ResponseCode.CREATED, ResponseMessage.CREATED, community.getCommunityNo());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            CrCommunityFaResDto responseBody = new CrCommunityFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
