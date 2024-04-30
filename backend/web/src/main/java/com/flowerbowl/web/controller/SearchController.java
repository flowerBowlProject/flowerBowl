package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.response.search.SearchCommunityResponseDto;
import com.flowerbowl.web.dto.response.search.SearchLessonResponseDto;
import com.flowerbowl.web.service.SearchService;
import com.flowerbowl.web.dto.response.search.SearchAllResponseDto;
import com.flowerbowl.web.dto.response.search.SearchRecipeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    // 전체 검색
    // 레시피 최신 4개, 클래스 4개, 커뮤니티 4개로 우선
    @GetMapping(value = "/search") // 비로그인 조회
    public ResponseEntity<? super SearchAllResponseDto> guestSearchAll(@PageableDefault(page = 0, size = 4) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword){
        return searchService.searchAll(pageable, keyword, false, "guest");
    }
    @GetMapping(value = "/user/search") // 로그인 조회
//    public ResponseEntity<? super AllResponseDto> search(@PathVariable String query){
    public ResponseEntity<? super SearchAllResponseDto> userSearchAll(@PageableDefault(page = 0, size = 4) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword, @AuthenticationPrincipal String userId){
        return searchService.searchAll(pageable, keyword, true, userId);
    }

    // 레시피 검색
    @GetMapping(value = "search/recipes") // 비로그인 조회
    public ResponseEntity<? super SearchRecipeResponseDto> searchRecipe(@PageableDefault(page = 0, size = 8) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword){
        return searchService.searchRecipe(pageable, keyword, false, "guest");
    }
    @GetMapping(value = "user/search/recipes") // 로그인 조회
    public ResponseEntity<? super SearchRecipeResponseDto> searchRecipe(@PageableDefault(page = 0, size = 8) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword, @AuthenticationPrincipal String userId){
        return searchService.searchRecipe(pageable, keyword, true, userId);
    }

    // 클래스 검색
    @GetMapping(value = "search/lessons") // 비로그인
    public ResponseEntity<? super SearchLessonResponseDto> searchLesson(@PageableDefault(page = 0, size = 8) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword){
        return searchService.searchLesson(pageable, keyword, false, "guest");
    }
    @GetMapping(value = "user/search/lessons") // 로그인
    public ResponseEntity<? super SearchLessonResponseDto> searchLesson(@PageableDefault(page = 0, size = 8) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword, @AuthenticationPrincipal String userId){
        return searchService.searchLesson(pageable, keyword, true, userId);
    }

    // 커뮤니티 검색
    @GetMapping(value = "search/communities") // 로그인, 비로그인 상관 x
    public ResponseEntity<? super SearchCommunityResponseDto> searchCommunity(@PageableDefault(page = 0, size = 8) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword){
        return searchService.searchCommunity(pageable, keyword);
    }
}