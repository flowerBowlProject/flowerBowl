package com.flowerbowl.web.banner;

import com.flowerbowl.web.banner.bannerDTO.BannerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    // 배너조회 // 모든 사용자 가능
    @GetMapping(value = "/api/banners")
//    public void banner(){
    public ResponseEntity<? super BannerResponseDto> banner(){
        return bannerService.banner();
//        System.out.println("test /api/banner");
//        UserBannerResponseDto userBannerResponseDto = new UserBannerResponseDto();
//        Banner banner = bannerService.banner();
//        if(banner.getBannerNo() == -1L){
//            userBannerResponseDto.setCode(400L);
//            userBannerResponseDto.setMessage("배너가 없습니다!");
//            return userBannerResponseDto;
//        }
//        userBannerResponseDto.setCode(200L);
//        userBannerResponseDto.setMessage("SU");
//        userBannerResponseDto.setBanner_content(banner.getBannerContent());
//        userBannerResponseDto.setBanner_sname(banner.getBannerFileSname());
//        return userBannerResponseDto;
        //        try{
//            Banner banner = bannerService.banner();
//            userBannerResponseDto.setCode(200L);
//            userBannerResponseDto.setMessage("SU");
//            userBannerResponseDto.setBanner_content(banner.getBannerContent());
//            userBannerResponseDto.setBanner_sname(banner().getBanner_sname());
//            System.out.println("banner조회 등록된 배너가 있는 경우");
//            return userBannerResponseDto; // 수정필요
//        }catch (Exception e){
//            System.out.println("banner조회 등록된 배너가 없는 경우");
//            userBannerResponseDto.setCode(400L);
//            userBannerResponseDto.setMessage("현재 등록된 배너가 없습니다!");
//            return userBannerResponseDto;
//        }
    }
}
