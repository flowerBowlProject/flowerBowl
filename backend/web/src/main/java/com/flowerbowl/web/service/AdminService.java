package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.admin.BannerRequestDto;
import com.flowerbowl.web.dto.request.admin.ChefAcceptRequestDto;
import com.flowerbowl.web.dto.response.admin.ChefResponseDto;
import com.flowerbowl.web.dto.response.admin.ResponseDto;
import com.flowerbowl.web.dto.response.search.SearchAllResponseDto;
import com.flowerbowl.web.dto.response.search.SearchCommunityResponseDto;
import com.flowerbowl.web.dto.response.search.SearchLessonResponseDto;
import com.flowerbowl.web.dto.response.search.SearchRecipeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Pageable;

public interface AdminService {

    ResponseEntity<? super ChefResponseDto> candidateList();
    ResponseEntity<ResponseDto> chefAccept(Long user_no);
    ResponseEntity<ResponseDto> chefRefuse(Long license_no);
    ResponseEntity<ResponseDto> banner(BannerRequestDto bannerRequestDto);
}
