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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    // 전체 검색
    // 레시피 최신 4개, 클래스 4개, 커뮤니티 4개로 우선
    @GetMapping(value = "")
//    public ResponseEntity<? super AllResponseDto> search(@PathVariable String query){
    public ResponseEntity<? super SearchAllResponseDto> searchAll(@PageableDefault(page = 0, size = 4) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword){
        return searchService.searchAll(pageable, keyword);
    }
    // 레시피 검색
    @GetMapping(value = "/recipes")
    public ResponseEntity<? super SearchRecipeResponseDto> searchRecipe(@PageableDefault(page = 0, size = 8) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword){
        return searchService.searchRecipe(pageable, keyword);
    }
    // 클래스 검색
    @GetMapping(value = "/lessons")
    public ResponseEntity<? super SearchLessonResponseDto> searchLesson(@PageableDefault(page = 0, size = 8) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword){
        System.out.println("controllerTest");
        System.out.println(keyword);
        return searchService.searchLesson(pageable, keyword);
    }
    // 커뮤니티 검색
    @GetMapping(value = "/communities")
    public ResponseEntity<? super SearchCommunityResponseDto> searchCommunity(@PageableDefault(page = 0, size = 8) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword){
        return searchService.searchCommunity(pageable, keyword);
    }
}