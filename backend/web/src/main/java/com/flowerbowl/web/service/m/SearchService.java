package com.flowerbowl.web.service.m;

import com.flowerbowl.web.dto.m.ResponseDto;
import com.flowerbowl.web.dto.m.lesson.LessonShortDto;
import com.flowerbowl.web.dto.m.search.*;
import com.flowerbowl.web.repository.m.JpaDataCommunityRepository;
import com.flowerbowl.web.repository.m.JpaDataLessonRepository;
import com.flowerbowl.web.repository.m.JpaDataRecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    private final JpaDataLessonRepository jpaDataLessonRepository;
    private final JpaDataRecipeRepository jpaDataRecipeRepository;
    private final JpaDataCommunityRepository jpaDataCommunityRepository;

    // 전체 검색
    // 레시피 테이블, 클래스 테이블, 커뮤니티 테이블 조회
    @Transactional
//    public ResponseEntity<? super AllResponseDto> searchAll(String query){
    public ResponseEntity<? super SearchAllResponseDto> searchAll(Pageable pageable, String keyword){
        try{
//            if(keyword == null){
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("FA", "검색어가 없습니다"));
//            }
            List<RecipeShortDto> recipeList = jpaDataRecipeRepository.findAllByRecipeTitleContainingOrRecipeContentContainingOrRecipeStuffContainingOrderByRecipeNoDesc(keyword, keyword, keyword, pageable)
                    .map(RecipeShortDto::from).getContent();
            List<LessonShortDto> lessonList = jpaDataLessonRepository.findAllByLessonTitleContainingOrLessonContentContainingOrderByLessonNo(keyword, keyword, pageable)
                    .map(LessonShortDto::from).getContent();
            List<CommunityShortDto> communityList = jpaDataCommunityRepository.findAllByCommunityTitleContainingOrCommunityContentContainingOrderByCommunityNoDesc(keyword, keyword, pageable)
                    .map(CommunityShortDto::from).getContent();
            return ResponseEntity.status(HttpStatus.OK).body(new SearchAllResponseDto("SU", "success", recipeList, lessonList, communityList));
        }catch (Exception e){
            log.info("SearchService searchAll exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 레시피 검색
    public ResponseEntity<? super SearchRecipeResponseDto> searchRecipe(Pageable pageable, String keyword){
        try{
            List<RecipeShortDto> recipeList = jpaDataRecipeRepository.findAllByRecipeTitleContainingOrRecipeContentContainingOrRecipeStuffContainingOrderByRecipeNoDesc(keyword, keyword, keyword, pageable)
                    .map(RecipeShortDto::from).getContent();
            return ResponseEntity.status(HttpStatus.OK).body(new SearchRecipeResponseDto("SU", "success", recipeList));
        }catch (Exception e){
            log.info("SearchService searchRecipe exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 클래스 검색
    public ResponseEntity<? super SearchLessonResponseDto> searchLesson(Pageable pageable, String keyword){
        try{
            List<LessonShortDto> lessonShortDtoList = jpaDataLessonRepository.findAllByLessonTitleContainingOrLessonContentContainingOrderByLessonNo(keyword, keyword, pageable)
                    .map(LessonShortDto::from).getContent();
            return ResponseEntity.status(HttpStatus.OK).body(new SearchLessonResponseDto("SU", "success", lessonShortDtoList));
        }catch (Exception e){
            log.info("SearchService searchLesson exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 커뮤니티 검색
    @Transactional
    public ResponseEntity<? super SearchCommunityResponseDto> searchCommunity(Pageable pageable, String keyword){
        try{
            List<CommunityShortDto> communityList = jpaDataCommunityRepository.findAllByCommunityTitleContainingOrCommunityContentContainingOrderByCommunityNoDesc(keyword, keyword, pageable)
                    .map(CommunityShortDto::from).getContent();
            return ResponseEntity.status(HttpStatus.OK).body(new SearchCommunityResponseDto("SU", "success", communityList));
        }catch (Exception e){
            log.info("SearchService searchAll exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
}
