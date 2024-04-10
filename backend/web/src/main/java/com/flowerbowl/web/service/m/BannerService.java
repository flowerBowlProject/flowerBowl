package com.flowerbowl.web.service.m;

import com.flowerbowl.web.domain.Banner;
import com.flowerbowl.web.dto.m.ResponseDto;
import com.flowerbowl.web.dto.m.admin.BannerResponseDto;
import com.flowerbowl.web.repository.m.BannerRepository;
import com.flowerbowl.web.repository.m.JpaDataBannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerService {
    private final BannerRepository bannerRepository;
    private final JpaDataBannerRepository jpaDataBannerRepository;
    @Transactional
    // 현재 배너정보를 제공해줌
    public ResponseEntity<? super BannerResponseDto> banner(){
        try{
            if(jpaDataBannerRepository.countAllByBannerNoGreaterThan(-1L) == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("BR", "등록된 배너가 없습니다"));
            }
            Banner banner = bannerRepository.findOne();
//            BannerResponseDto bannerResponseDto = new BannerResponseDto();
            return ResponseEntity.status(HttpStatus.OK).body(new BannerResponseDto("SU","success", banner.getBannerFileSname(), banner.getBannerFileOname(), banner.getBannerContent()));
        }catch(Exception e){
//            Banner banner = new Banner();
//            banner.setBannerNo(-1L);
//            System.out.println("banner service exceoption");
            log.info("BannerService exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
}
