package com.flowerbowl.web.controller.m;

import com.flowerbowl.web.dto.m.ResponseDto;
import com.flowerbowl.web.dto.m.admin.BannerRequestDto;
import com.flowerbowl.web.dto.m.admin.ChefAcceptRequestDto;
import com.flowerbowl.web.dto.m.admin.ChefResponseDto;
import com.flowerbowl.web.service.m.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // 쉐프후보 전체 조회
    @GetMapping(value = "/chefs")
    public ResponseEntity<? super ChefResponseDto> adminChefList(){
        return adminService.candidateList();
    }

    // 관리자_쉐프승인
    @PutMapping(value = "/chefs")
    public ResponseEntity<ResponseDto> adminAcceptChef(@RequestBody ChefAcceptRequestDto chefAcceptRequestDto){
        return adminService.chefAccept(chefAcceptRequestDto.getUser_no());
    }

    // 관리자 쉐프거절
    @DeleteMapping(value = "chefs/{license_no}")
    public ResponseEntity<ResponseDto> DeleteVariable(@PathVariable Long license_no){
        return adminService.chefRefuse(license_no);
    }

    // 관리자 배너등록
    @PutMapping(value = "/banners")
    public ResponseEntity<ResponseDto> adminRegisterBanners(@RequestBody BannerRequestDto bannerRequestDto){
        return adminService.banner(bannerRequestDto);
    }
}
