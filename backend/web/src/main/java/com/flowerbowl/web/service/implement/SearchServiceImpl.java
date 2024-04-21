package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.Lesson;
import com.flowerbowl.web.domain.Recipe;
import com.flowerbowl.web.dto.object.search.PageInfo;
import com.flowerbowl.web.dto.response.search.ResponseDto;
import com.flowerbowl.web.dto.object.search.LessonShortDto;
import com.flowerbowl.web.dto.object.search.CommunityShortDto;
import com.flowerbowl.web.dto.object.search.RecipeShortDto;
import com.flowerbowl.web.dto.response.search.SearchAllResponseDto;
import com.flowerbowl.web.dto.response.search.SearchCommunityResponseDto;
import com.flowerbowl.web.dto.response.search.SearchLessonResponseDto;
import com.flowerbowl.web.dto.response.search.SearchRecipeResponseDto;
import com.flowerbowl.web.repository.CommentRepository;
import com.flowerbowl.web.repository.LessonLikeRepository;
import com.flowerbowl.web.repository.RecipeLikeRepository;
import com.flowerbowl.web.repository.search.JpaDataCommunityRepository;
import com.flowerbowl.web.repository.search.JpaDataLessonRepository;
import com.flowerbowl.web.repository.search.JpaDataRecipeRepository;
import com.flowerbowl.web.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final JpaDataLessonRepository jpaDataLessonRepository;
    private final JpaDataRecipeRepository jpaDataRecipeRepository;
    private final JpaDataCommunityRepository jpaDataCommunityRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    private final LessonLikeRepository lessonLikeRepository;
    private final CommentRepository commentRepository;
    // 전체 검색
    // 레시피 테이블, 클래스 테이블, 커뮤니티 테이블 조회
    @Override
    public ResponseEntity<? super SearchAllResponseDto> searchAll(Pageable pageable, String keyword, Boolean loginStatus, String userId){
        try{
            List<RecipeShortDto> recipeList = jpaDataRecipeRepository.recipeSearch(keyword, pageable)
                    .map(RecipeShortDto::from).getContent();
            for(RecipeShortDto iter : recipeList){
                Long recipe_no = iter.getRecipe_no();
                iter.setRecipe_likes_num(recipeLikeRepository.countAllByRecipe_RecipeNo(recipe_no));
                iter.setRecipe_comments_num(commentRepository.countAllByRecipe_RecipeNo(recipe_no));
                if(loginStatus){ // 로그인 상태이면 // test OK
                    iter.setRecipe_likes_status(recipeLikeRepository.existsRecipeLikeByRecipe_RecipeNoAndUser_UserId(recipe_no, userId));
                }else{
                    iter.setRecipe_likes_status(false);
                }
            }
            List<LessonShortDto> lessonList = jpaDataLessonRepository.findLessonByLessonDeleteStatusAndLessonTitleContainingOrLessonContentContainingOrderByLessonNoDesc(false, keyword, keyword, pageable)
                    .map(LessonShortDto::from).getContent();
//            List<LessonShortDto> lessonList = jpaDataLessonRepository.findAllByLessonTitleContainingOrLessonContentContainingOrderByLessonNo(keyword, keyword, pageable)
//                    .map(LessonShortDto::from).getContent();
            for(LessonShortDto iter : lessonList){
                Long lessonNo = iter.getLesson_no();
                iter.setLesson_likes_num(lessonLikeRepository.countLessonLikeByLesson_LessonNo(lessonNo));
                if(loginStatus) { // 로그인 상태이면
                    iter.setLesson_likes_status(lessonLikeRepository.existsLessonLikeByLesson_LessonNoAndUser_UserId(lessonNo, userId));
                }else{
                    iter.setLesson_likes_status(false);
                }
            }
            List<CommunityShortDto> communityList = jpaDataCommunityRepository.findAllByCommunityTitleContainingOrCommunityContentContainingOrderByCommunityNoDesc(keyword, keyword, pageable)
                    .map(CommunityShortDto::from).getContent();
            return ResponseEntity.status(HttpStatus.OK).body(new SearchAllResponseDto("SU", "success", recipeList, lessonList, communityList));
        }catch (Exception e){
            log.info("SearchService searchLesson exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 레시피 검색
    @Override
    public ResponseEntity<? super SearchRecipeResponseDto> searchRecipe(Pageable pageable, String keyword, Boolean loginStatus, String userId){
        try{
            Page<Recipe> page = jpaDataRecipeRepository.recipeSearch(keyword, pageable);
            PageInfo pageInfo = new PageInfo(page.getTotalPages(), page.getTotalElements());
            List<RecipeShortDto> recipeList = page.map(RecipeShortDto::from).getContent();
            // 좋아요 수, 댓글 수, 좋아요 상태 // test OK
            for(RecipeShortDto iter : recipeList){
                Long recipe_no = iter.getRecipe_no();
                // 좋아요 수 // test OK
                iter.setRecipe_likes_num(recipeLikeRepository.countAllByRecipe_RecipeNo(recipe_no));
                log.info("recipe_no : {}, likes_num : {}",recipe_no, recipeLikeRepository.countAllByRecipe_RecipeNo(recipe_no));
                // 댓글 수 // test OK
                iter.setRecipe_comments_num(commentRepository.countAllByRecipe_RecipeNo(recipe_no));
                // 좋아요 상태 // test OK
                if(loginStatus){ // 로그인 상태이면 // test OK
                    iter.setRecipe_likes_status(recipeLikeRepository.existsRecipeLikeByRecipe_RecipeNoAndUser_UserId(recipe_no, userId));
                }else{ // 비로그인 // test OK
                    iter.setRecipe_likes_status(false);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new SearchRecipeResponseDto("SU", "success", pageInfo, recipeList));
        }catch (Exception e){
            log.info("SearchService searchRecipe exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 클래스 검색
    @Override
    public ResponseEntity<? super SearchLessonResponseDto> searchLesson(Pageable pageable, String keyword, Boolean loginStatus, String userId){
        try{
//            Page<Lesson> page = jpaDataLessonRepository.findAllByLessonTitleContainingOrLessonContentContainingOrderByLessonNo(keyword, keyword, pageable);
//            Page<Lesson> page = jpaDataLessonRepository.findAllByLessonTitleContainingOrLessonContentContainingAndLessonDeleteStatusOrderByLessonNo(keyword, keyword, false, pageable);
            Page<Lesson> page = jpaDataLessonRepository.findLessonByLessonDeleteStatusAndLessonTitleContainingOrLessonContentContainingOrderByLessonNoDesc(false, keyword, keyword, pageable);
            PageInfo pageInfo = new PageInfo(page.getTotalPages(), page.getTotalElements());
            List<LessonShortDto> lessonList = page.map(LessonShortDto::from).getContent();
            // 즐겨찾기 수
            for(LessonShortDto iter : lessonList){
                Long lessonNo = iter.getLesson_no();
                iter.setLesson_likes_num(lessonLikeRepository.countLessonLikeByLesson_LessonNo(lessonNo));
                if(loginStatus) { // 로그인 상태이면
                    log.info("lesson test 로그인상태");
                    iter.setLesson_likes_status(lessonLikeRepository.existsLessonLikeByLesson_LessonNoAndUser_UserId(lessonNo, userId));
                }else{
                    log.info("lesson test 게스트상태");
                    iter.setLesson_likes_status(false);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new SearchLessonResponseDto("SU", "success",pageInfo, lessonList));
        }catch (Exception e){ // ```
            log.info("SearchService searchLesson exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
    // 커뮤니티 검색
    @Override
    public ResponseEntity<? super SearchCommunityResponseDto> searchCommunity(Pageable pageable, String keyword){
        try{
            Page<Community> page = jpaDataCommunityRepository.findAllByCommunityTitleContainingOrCommunityContentContainingOrderByCommunityNoDesc(keyword, keyword, pageable);
            PageInfo pageInfo = new PageInfo(page.getTotalPages(), page.getTotalElements());
            List<CommunityShortDto> communityList = page.map(CommunityShortDto::from).getContent();
//            List<CommunityShortDto> communityList = jpaDataCommunityRepository.findAllByCommunityTitleContainingOrCommunityContentContainingOrderByCommunityNoDesc(keyword, keyword, pageable)
//                    .map(CommunityShortDto::from).getContent();
            return ResponseEntity.status(HttpStatus.OK).body(new SearchCommunityResponseDto("SU", "success", pageInfo, communityList));
        }catch (Exception e){
            log.info("SearchService searchAll exception : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("ISE", "Internal Server Error"));
        }
    }
}
