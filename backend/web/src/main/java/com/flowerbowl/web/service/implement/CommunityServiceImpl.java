package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.CommunityFile;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.object.community.CreateCommunityDto;
import com.flowerbowl.web.dto.object.community.CreateCommunityFileDto;
import com.flowerbowl.web.dto.request.community.CrCommunityReqDto;
import com.flowerbowl.web.dto.request.community.UpCommunityReqDto;
import com.flowerbowl.web.dto.response.community.*;
import com.flowerbowl.web.handler.CommunityNotFoundException;
import com.flowerbowl.web.handler.UserNotFoundException;
import com.flowerbowl.web.repository.CommunityFileRepository;
import com.flowerbowl.web.repository.CommunityRepository;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.service.CommunityService;
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

    @Override
    public ResponseEntity<? extends CommunityResponseDto> updateCommunity(UpCommunityReqDto request, Long community_no) throws Exception {
        // 해당 커뮤니티 게시글의 작성자가 token으로부터 얻은 사용자와 일치하는지 검증하는 코드 필요
        try {
            // 커뮤니티 번호로 커뮤니티 찾기
            Community community = communityRepository.findByCommunityNo(community_no).orElseThrow(CommunityNotFoundException::new);
            // 커뮤니티 번호로 커뮤니티 파일 찾기
            CommunityFile communityFile = communityFileRepository.findByCommunity_CommunityNo(community_no);

            // 찾은 커뮤니티 게시글의 데이터를 수정
            community.updateTitle(request.getCommunityTitle());
            community.updateContent(request.getCommunityContent());

            // 찾은 커뮤니티 파일의 데이터를 수정
            communityFile.updateFileOname(request.getCommunityFileOname());
            communityFile.updateFileSname(request.getCommunityFileSname());

            // 수정된 커뮤니티 데이터 DB에 저장
            Community result = communityRepository.save(community);
            // 수정된 커뮤니티 파일 데이터 DB에 저장
            communityFileRepository.save(communityFile);

            UpCommunitySuResDto responseBody = new UpCommunitySuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, result.getCommunityNo());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            UpCommunityFaResDto responseBody = new UpCommunityFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends CommunityResponseDto> deleteCommunity(Long community_no) throws Exception {
        // 해당 커뮤니티 게시글의 작성자가 token으로부터 얻은 사용자와 일치하는지 검증하는 코드 필요
        try {
            Community community = communityRepository.findByCommunityNo(community_no).orElseThrow(CommunityNotFoundException::new);
            communityRepository.delete(community);

            DelCommunityResDto responseBody = new DelCommunityResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            DelCommunityResDto responseBody = new DelCommunityResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

}
