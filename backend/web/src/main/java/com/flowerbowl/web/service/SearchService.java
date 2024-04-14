package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.response.search.SearchAllResponseDto;
import com.flowerbowl.web.dto.response.search.SearchCommunityResponseDto;
import com.flowerbowl.web.dto.response.search.SearchLessonResponseDto;
import com.flowerbowl.web.dto.response.search.SearchRecipeResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SearchService {
    ResponseEntity<? super SearchAllResponseDto> searchAll(Pageable pageable, String keyword);

    ResponseEntity<? super SearchRecipeResponseDto> searchRecipe(org.springframework.data.domain.Pageable pageable, String keyword);

    ResponseEntity<? super SearchLessonResponseDto> searchLesson(org.springframework.data.domain.Pageable pageable, String keyword);
    ResponseEntity<? super SearchCommunityResponseDto> searchCommunity(Pageable pageable, String keyword);
}
