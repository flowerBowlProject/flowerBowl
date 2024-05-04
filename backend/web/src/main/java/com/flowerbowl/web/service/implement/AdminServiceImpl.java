package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.Banner;
import com.flowerbowl.web.domain.License;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.response.admin.ResponseDto;
import com.flowerbowl.web.dto.request.admin.BannerRequestDto;
import com.flowerbowl.web.dto.object.admin.ChefCandidiateDto;
import com.flowerbowl.web.dto.response.admin.ChefResponseDto;
import com.flowerbowl.web.repository.admin.*;
import com.flowerbowl.web.repository.banner.BannerJpaDataRepository;
import com.flowerbowl.web.service.AdminService;
import com.flowerbowl.web.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.flowerbowl.web.domain.Role.ROLE_CHEF;


@Service
@Slf4j
@RequiredArgsConstructor // final로 선언된 변수 생성자를 자동으로 생성해줌
public class AdminServiceImpl implements AdminService {
    private final AdminBannerRepository adminBannerRepository;
    private final AdminLicenseJpaDataRepository adminLicenseJpaDataRepository;
    private final AdminUserJpaDataRepository adminUserJpaDataRepository;
    private final AdminBannerJpaDataRepository adminBannerJpaDataRepository;
    private final ImageService imageService;

    // 쉐프 신청한 회원 전체 조회
    @Override
    public ResponseEntity<? super ChefResponseDto> candidateList() {
        try{
            List<License> licenseList = adminLicenseJpaDataRepository.findAllByLicenseStatus(false);
            List<ChefCandidiateDto> chefCandidiateDtoList = licenseList.stream().map(ChefCandidiateDto::from).toList();

            ChefResponseDto chefResponseDto = new ChefResponseDto();
            chefResponseDto.setCode("SU");
            chefResponseDto.setMessage("success");
            chefResponseDto.setCandidiate(chefCandidiateDtoList);
//            for(License tmp : license){
//                ChefCandidiateDto chefCandidiateDto = new ChefCandidiateDto();
//                chefCandidiateDto.setLicense_no(tmp.getLicenseNo());
//                chefCandidiateDto.setLicense_date(tmp.getLicenseDate());
//                chefCandidiateDto.setLicense_sname(tmp.getLicenseFileSname());
//                chefCandidiateDto.setLicense_oname(tmp.getLicenseFileOname());
//                chefCandidiateDto.setLicense_status(false);
//                chefCandidiateDto.setUser_no(tmp.getUser().getUserNo());
//                chefCandidiateDto.setUser_name(tmp.getUser().getUserNickname());
//
//                chefResponseDto.getCandidiate().add(chefCandidiateDto);
//            }
            return ResponseEntity.status(HttpStatus.OK).body(chefResponseDto);
        }catch (Exception e){
            log.info("adminService candidiateList : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 쉐프 승인
    @Transactional
    @Override
    public ResponseEntity<ResponseDto> chefAccept(Long user_no){
        log.info("user_no : {}",user_no);
        try{
            //   user_no이 존재하지 않는 경우
            Long cnt = adminLicenseJpaDataRepository.countLicenseByUser_UserNo(user_no);
            if(cnt == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당 유저는 신청목록에 없습니다."));
            }else if(cnt >= 2){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "DB error : 해당 유저에 대한 license 정보가 2번 이상 있습니다."));
            }
            // 이거 jpa data가 아닌 jpa로
//            License license = jpaLicenseRepository.findByUserNo(user_no);
            License license = adminLicenseJpaDataRepository.findByUser_UserNo(user_no);
            // 해당 유저가 이미 쉐프인 경우
            if(license.getLicenseStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("BR","해당 유저는 이미 쉐프입니다."));
            }
            // 해당 유저를 쉐프로 바꿔줌
            license.setLicenseStatus(true); // User table도 변경해야함

            // User table에서 해당 유저의 역할을 쉐프로 바꿔줌
//            User user = adminUserJpaRepository.findByUserNo(user_no);
            User user = adminUserJpaDataRepository.findUserByUserNo(user_no);
            user.setUserRole(ROLE_CHEF);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
        }catch (Exception e){
            log.info("exception");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 쉐프 거절
    @Transactional
    @Override
    public ResponseEntity<ResponseDto> chefRefuse(Long license_no){
//        Assert.notNull(license_no, "ID_MUST_NOT_BE_NULL");

        try{
            if(!adminLicenseJpaDataRepository.existsLicenseByLicenseNo(license_no)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당 유저는 신청목록에 없습니다."));
            }
            if(adminLicenseJpaDataRepository.findLicenseByLicenseNo(license_no).getLicenseStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "해당 유저는 이미 쉐프입니다."));
            }
            adminLicenseJpaDataRepository.deleteLicenseByLicenseNo(license_no);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("SU", "success"));
        }catch (Exception e){
            log.info("AdminService chefRefuse exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }

    // 배너 등록
    @Transactional
    @Override
    public ResponseEntity<ResponseDto> banner(BannerRequestDto bannerRequestDto){
        try{
            // oname : temp/banner/파일명,
            String requestBannerOname = bannerRequestDto.getAdmin_banner_oname();
            String requestBannerSname = bannerRequestDto.getAdmin_banner_sname();
            if(requestBannerOname == null || requestBannerOname.trim().isEmpty() || requestBannerSname == null || requestBannerSname.trim().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "request에 배너가 존재하지 않습니다."));
            }
            // 기존에 등록된 배너가 있는 경우
            if(!(adminBannerJpaDataRepository.countAllByBannerNoGreaterThan(-1L) == 0)){
                List<Banner> bannerList = adminBannerJpaDataRepository.findAll();
                for(Banner iter : bannerList){
                    // S3에서 삭제
                    log.info(iter.getBannerFileOname());
                    imageService.deleteS3(iter.getBannerFileOname());

                    // DB에서 삭제
                    adminBannerJpaDataRepository.delete(iter);
                }
            }

            // banner/파일명
            String newBannerOname = "banner/" + requestBannerOname.split("/")[2];
            String newBannerSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newBannerOname;
            // S3에 넣어줌
            imageService.copyS3(requestBannerOname, newBannerOname);

            // DB에 넣어줌
            Banner banner = new Banner();
            banner.setBannerContent(bannerRequestDto.getAdmin_content());
            banner.setBannerFileSname(newBannerSname);
            banner.setBannerFileOname(newBannerOname);
            adminBannerRepository.bannerRegister(banner);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());

        }catch (Exception e){
            log.info("AdminService banner error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
//        catch (Exception e){
//            System.out.println("Banner exception e");
//        }
    }
}