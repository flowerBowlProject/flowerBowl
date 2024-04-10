package com.flowerbowl.web.banner;

import com.flowerbowl.web.banner.bannerRepository.BannerJpaDataRepository;
import com.flowerbowl.web.banner.bannerRepository.BannerRepository;
import com.flowerbowl.web.domain.Banner;
import com.flowerbowl.web.commonDTO.ResponseDto;
import com.flowerbowl.web.banner.bannerDTO.BannerResponseDto;
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
    private final BannerJpaDataRepository bannerJpaDataRepository;
    @Transactional
    // 현재 배너정보를 제공해줌
    public ResponseEntity<? super BannerResponseDto> banner(){
        try{
            if(bannerJpaDataRepository.countAllByBannerNoGreaterThan(-1L) == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("BR", "등록된 배너가 없습니다"));
            }
            Banner banner = bannerRepository.findOne();
            return ResponseEntity.status(HttpStatus.OK).body(new BannerResponseDto("SU","success", banner.getBannerFileSname(), banner.getBannerFileOname(), banner.getBannerContent()));
        }catch(Exception e){
            log.info("BannerService exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
}
