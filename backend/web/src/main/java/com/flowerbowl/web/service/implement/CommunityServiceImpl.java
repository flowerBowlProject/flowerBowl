package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.CommunityFile;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.object.community.*;
import com.flowerbowl.web.dto.request.community.CrCommunityReqDto;
import com.flowerbowl.web.dto.request.community.UpCommunityReqDto;
import com.flowerbowl.web.dto.response.community.*;
import com.flowerbowl.web.handler.CommunityNotFoundException;
import com.flowerbowl.web.handler.DoesNotMatchException;
import com.flowerbowl.web.handler.PageNotFoundException;
import com.flowerbowl.web.handler.UserNotFoundException;
import com.flowerbowl.web.repository.CommunityFileRepository;
import com.flowerbowl.web.repository.CommunityRepository;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.service.CommunityService;
import com.flowerbowl.web.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityFileRepository communityFileRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Override
    public ResponseEntity<? extends CommunityResponseDto> createCommunity(CrCommunityReqDto request, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }

            List<String> fileOname = null;
            List<String> fileSname = null;
            String content = request.getCommunity_content();

            // request의 community_file_oname과 community_file_sname이 null이 아닐 때 해당 값으로 community file 생성 후 DB에 저장
            if (!CollectionUtils.isEmpty(request.getCommunity_file_oname()) && !CollectionUtils.isEmpty(request.getCommunity_file_sname())) {
                fileOname = new ArrayList<>();
                fileSname = new ArrayList<>();

                for (String source : request.getCommunity_file_sname()) {
                    source = source.trim();
                    if (source.isEmpty()) {
                        continue;
                    }

                    // request의 file_sname 리스트를 순회하며 업로드된 이미지가 실제로 사용됐는지 확인한다.
                    if (content.contains(source)) {
                        // file_sname에서 파일명을 가져오기 위해 "/"로 나누고 마지막 인덱스를 가져온다.
                        int lastIdx = source.split("/").length - 1;
                        String fileName = source.split("/")[lastIdx];

                        // oldFileOname => temp/content/파일명
                        // newFileOname => community/파일명
                        String oldFileOname = "temp/content/" + fileName;
                        String newFileOname = "community/" + fileName;

                        // temp/content/에 있는 파일을 community/로 복사
                        imageService.copyS3(oldFileOname, newFileOname);

                        // 복사된 새 파일명을 가지고 새 url을 생성
                        String newFileSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newFileOname;

                        // content에 포함된 기존 url을 새로운 url로 대체
                        content = content.replace(source, newFileSname);

                        // DB에 저장할 리스트에 새로운 file_oname, file_sname을 push
                        fileOname.add(newFileOname);
                        fileSname.add(newFileSname);
                    }
                }
            }

            // request의 값으로 Community Dto 생성
            CreateCommunityDto createCommunityDto = new CreateCommunityDto(
                    request.getCommunity_title(),
                    content,
                    LocalDate.now(ZoneId.of("Asia/Seoul")),
                    user.getUserNickname(),
                    0L,
                    user
            );

            // 생성된 community를 db에 저장 후 객체 반환
            Community result = communityRepository.save(createCommunityDto.toEntity());

            if (fileOname != null) {
                CreateCommunityFileDto createCommunityFileDto = new CreateCommunityFileDto(fileOname, fileSname, result);
                communityFileRepository.save(createCommunityFileDto.toEntity());
            }

            CrCommunitySuResDto responseBody = new CrCommunitySuResDto(ResponseCode.CREATED, ResponseMessage.CREATED, result.getCommunityNo());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            CrCommunityFaResDto responseBody = new CrCommunityFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            CrCommunityFaResDto responseBody = new CrCommunityFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends CommunityResponseDto> updateCommunity(UpCommunityReqDto request, Long community_no, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }

            // 커뮤니티 번호로 커뮤니티 찾기
            Community community = communityRepository.findByCommunityNo(community_no).orElseThrow(CommunityNotFoundException::new);
            // 커뮤니티 번호로 커뮤니티 파일 찾기
            CommunityFile communityFile = communityFileRepository.findByCommunity_CommunityNo(community_no);

            if (!user.getUserNo().equals(community.getUser().getUserNo())) {
                throw new DoesNotMatchException();
            }

            List<String> fileOname = null;
            List<String> fileSname = null;
            String content = request.getCommunity_content();

            if (communityFile != null) {
                // 기존 community_file data가 있고 request의 community_file_oname과 community_file_sname이 null이 아니라면 해당 값으로 community file 수정 후 db에 저장
                if (!CollectionUtils.isEmpty(request.getCommunity_file_oname()) && !CollectionUtils.isEmpty(request.getCommunity_file_sname())) {
                    fileOname = new ArrayList<>();
                    fileSname = new ArrayList<>();

                    List<String> communityFileSnameList = communityFile.getCommunityFileSname();

                    for (String source : request.getCommunity_file_sname()) {
                        source = source.trim();
                        if (source.isEmpty()) {
                            continue;
                        }

                        int lastIdx = source.split("/").length - 1;
                        String fileName = source.split("/")[lastIdx];

                        if (communityFileSnameList.contains(source)) { // 해당 이미지 정보가 기존 file 리스트에 있는 경우. 즉 기존에 있던 이미지인 경우
                            String communityFileOname = "community/" + fileName;

                            if (content.contains(source)) { // content에 있는 경우. 즉 여전히 사용하는 이미지인 경우
                                fileOname.add(communityFileOname);
                                fileSname.add(source);
                            } else { // content에 없는 경우. 즉 내용에서 삭제된, 사용하지 않는 이미지인 경우. S3에서 해당 이미지를 삭제한다.
                                imageService.deleteS3(communityFileOname);
                            }
                        } else { // 해당 이미지 정보가 기존 file 리스트에 없는 경우. 즉 새로 추가된 이미지를 뜻함
                            if (content.contains(source)) {
                                // oldFileOname => temp/content/파일명
                                // newFileOname => community/파일명
                                String oldFileOname = "temp/content/" + fileName;
                                String newFileOname = "community/" + fileName;

                                // temp/content/에 있는 파일을 community/로 복사
                                imageService.copyS3(oldFileOname, newFileOname);

                                // 복사된 새 파일명을 가지고 새 url을 생성
                                String newFileSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newFileOname;

                                // content에 포함된 기존 url을 새로운 url로 대체
                                content = content.replace(source, newFileSname);

                                // DB에 저장할 리스트에 새로운 file_oname, file_sname을 push
                                fileOname.add(newFileOname);
                                fileSname.add(newFileSname);
                            }
                        }
                    }

                    if (CollectionUtils.isEmpty(fileOname) || CollectionUtils.isEmpty(fileSname)) { // for문을 돌고도 fileOname과 fileSname이 빈 리스트라면 모두 사용되지 않기 때문에 data를 DB에서 삭제
                        communityFileRepository.delete(communityFile);
                    }

                } else { // 기존 community_file data가 있고 request의 community_file_oname 또는 community_file_sname이 null이라면 잘못된 요청이다.
                    throw new IllegalArgumentException();
                }
            } else {
                // 기존 community_file data가 없고 request의 community_file_oname과 community_file_sname이 null이 아니라면 해당 값으로 새로운 community_file data 생성
                // 모두 새로운 이미지라는 것을 의미
                if (!CollectionUtils.isEmpty(request.getCommunity_file_oname()) && !CollectionUtils.isEmpty(request.getCommunity_file_sname())) {
                    fileOname = new ArrayList<>();
                    fileSname = new ArrayList<>();

                    for (String source : request.getCommunity_file_sname()) {
                        source = source.trim();
                        if (source.isEmpty()) {
                            continue;
                        }

                        if (content.contains(source)) {
                            int lastIdx = source.split("/").length - 1;
                            String fileName = source.split("/")[lastIdx];

                            String oldFileOname = "temp/content/" + fileName;
                            String newFileOname = "community/" + fileName;

                            imageService.copyS3(oldFileOname, newFileOname);

                            String newFileSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newFileOname;

                            content = content.replace(source, newFileSname);

                            fileOname.add(newFileOname);
                            fileSname.add(newFileSname);
                        }
                    }
                }
            }

            // 찾은 커뮤니티 게시글의 데이터를 수정
            community.updateTitle(request.getCommunity_title());
            community.updateContent(content);

            // 수정된 커뮤니티 데이터 DB에 저장
            Community result = communityRepository.save(community);

            if (fileOname != null) {
                if (communityFile != null) {
                    communityFile.updateFileOname(fileOname);
                    communityFile.updateFileSname(fileSname);

                    communityFileRepository.save(communityFile);
                } else {
                    CreateCommunityFileDto createCommunityFileDto = new CreateCommunityFileDto(fileOname, fileSname, result);
                    communityFileRepository.save(createCommunityFileDto.toEntity());
                }
            }

            UpCommunitySuResDto responseBody = new UpCommunitySuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, result.getCommunityNo());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            UpCommunityFaResDto responseBody = new UpCommunityFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (CommunityNotFoundException e) {
            logPrint(e);

            UpCommunityFaResDto responseBody = new UpCommunityFaResDto(ResponseCode.NOT_EXIST_COMMUNITY, ResponseMessage.NOT_EXIST_COMMUNITY);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (DoesNotMatchException e) {
            logPrint(e);

            UpCommunityFaResDto responseBody = new UpCommunityFaResDto(ResponseCode.DOES_NOT_MATCH, ResponseMessage.DOES_NOT_MATCH);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            UpCommunityFaResDto responseBody = new UpCommunityFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends CommunityResponseDto> deleteCommunity(Long community_no, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            Community community = communityRepository.findByCommunityNo(community_no).orElseThrow(CommunityNotFoundException::new);
            CommunityFile communityFile = communityFileRepository.findByCommunity_CommunityNo(community_no);

            if (!user.getUserNo().equals(community.getUser().getUserNo())) {
                throw new DoesNotMatchException();
            }

            if (communityFile != null) {
                for (String source : communityFile.getCommunityFileOname()) {
                    imageService.deleteS3(source);
                }
            }

            communityRepository.delete(community);

            DelCommunityResDto responseBody = new DelCommunityResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            DelCommunityResDto responseBody = new DelCommunityResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (CommunityNotFoundException e) {
            logPrint(e);

            DelCommunityResDto responseBody = new DelCommunityResDto(ResponseCode.NOT_EXIST_COMMUNITY, ResponseMessage.NOT_EXIST_COMMUNITY);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (DoesNotMatchException e) {
            logPrint(e);

            DelCommunityResDto responseBody = new DelCommunityResDto(ResponseCode.DOES_NOT_MATCH, ResponseMessage.DOES_NOT_MATCH);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            DelCommunityResDto responseBody = new DelCommunityResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends CommunityResponseDto> getAllCommunities(int page, int size) throws Exception {
        try {
            // parameter로 받은 page(페이지 번호 - 1), size(페이지 당 데이터 개수)로 PageRequest 생성
            PageRequest pageRequest = PageRequest.of(page, size);

            // page, size 정보를 가지고 해당하는 커뮤니티 게시글 조회
            Page<Community> communities = communityRepository.findAllByOrderByCommunityNoDesc(pageRequest);

            // 요청한 페이지 번호가 존재하는 페이지 개수를 넘을 때 Exception throw
            if (communities.getTotalPages() != 0 && page >= communities.getTotalPages()) {
                throw new PageNotFoundException();
            }

            // 조회한 커뮤니티 게시글 목록을 순회하며 각각의 게시글의 정보로 posts를 빌드
            List<GetAllCommunitiesDto> posts = communities.stream().map((community -> {
                return GetAllCommunitiesDto.builder()
                        .community_no(community.getCommunityNo())
                        .community_title(community.getCommunityTitle())
                        .community_writer(community.getCommunityWriter())
                        .community_date(community.getCommunityDate())
                        .community_views(community.getCommunityViews())
                        .build();
            })).toList();

            // 조회한 커뮤니티 게시글 목록에 대한 정보로 페이지 정보 빌드
            CommunityPageInfo communityPageInfo = CommunityPageInfo.builder()
                    .page(page + 1)
                    .size(size)
                    .totalPage(communities.getTotalPages())
                    .totalElement(communities.getTotalElements())
                    .build();

            GetAllCommunitiesSuResDto responseBody = new GetAllCommunitiesSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, posts, communityPageInfo);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (PageNotFoundException e) {
            logPrint(e);

            GetAllCommunitiesFaResDto responseBody = new GetAllCommunitiesFaResDto(ResponseCode.NOT_EXIST_PAGE, ResponseMessage.NOT_EXIST_PAGE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetAllCommunitiesFaResDto responseBody = new GetAllCommunitiesFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends CommunityResponseDto> getCommunity(Long community_no) throws Exception {
        try {
            Community community = communityRepository.findByCommunityNo(community_no).orElseThrow(CommunityNotFoundException::new);
            CommunityFile communityFile = communityFileRepository.findByCommunity_CommunityNo(community_no);

            // communityFile이 null일 경우
            List<String> communityFileOname = null;
            List<String> communityFileSname = null;
            if (communityFile != null) {
                communityFileOname = communityFile.getCommunityFileOname();
                communityFileSname = communityFile.getCommunityFileSname();
            }

            // 조회 수 증가
            community.updateView(community.getCommunityViews() + 1);
            communityRepository.save(community);

            GetCommunityDto newDto = GetCommunityDto.builder()
                    .community_no(community.getCommunityNo())
                    .community_title(community.getCommunityTitle())
                    .community_writer(community.getCommunityWriter())
                    .community_date(community.getCommunityDate())
                    .community_content(community.getCommunityContent())
                    .community_file_oname(communityFileOname)
                    .community_file_sname(communityFileSname)
                    .build();

            GetCommunitySuResDto responseBody = new GetCommunitySuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, newDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (CommunityNotFoundException e) {
            logPrint(e);

            GetCommunityFaResDto responseBody = new GetCommunityFaResDto(ResponseCode.NOT_EXIST_COMMUNITY, ResponseMessage.NOT_EXIST_COMMUNITY);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetCommunityFaResDto responseBody = new GetCommunityFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    private void logPrint(Exception e) {
        log.error("Exception [Err_Msg]: {}", e.getMessage());
        log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);
    }

}
