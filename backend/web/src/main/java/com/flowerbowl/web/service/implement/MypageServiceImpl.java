package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.*;
import com.flowerbowl.web.dto.object.mypage.LikeLessons;
import com.flowerbowl.web.dto.object.mypage.LikeRecipes;
import com.flowerbowl.web.dto.response.ResponseDto;
import com.flowerbowl.web.dto.response.mypage.GetLessonLikesResponseDto;
import com.flowerbowl.web.dto.response.mypage.GetRecipeLikesResponseDto;
import com.flowerbowl.web.repository.*;
import com.flowerbowl.web.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final UserRepository userRepository;
    private final LessonLikeRepository lessonLikeRepository;
    private final LessonRepository lessonRepository;
    private final RecipeLikeRepository reviewLikeRepository;
    private final RecipeRepository recipeRepository;


    /**
     * 개인적으로 로직이 너무 아쉬움 view 테이블로 하면 좋을 거 같음 나중에 리팩토링해보자
     */
    @Override
    public ResponseEntity<? super GetLessonLikesResponseDto> getLessonLikes(String userId) {

        List<LikeLessons> lessons = new ArrayList<>();

        try {
            User user = userRepository.findByUserId(userId);
            Long userNo = user.getUserNo();
            List<LessonLike> lessonLikeList = lessonLikeRepository.findAllByUser_UserNo(userNo);
            if (lessonLikeList == null) {GetLessonLikesResponseDto.noExistLesson();}


            List<Long> lessonNos = new ArrayList<>();
            for (LessonLike list : lessonLikeList) {
                lessonNos.add(list.getLesson().getLessonNo());
            }

            List<Lesson> lessonList = lessonRepository.findAllByLessonNoIn(lessonNos);
            for(Lesson lesson : lessonList){
                LikeLessons lessonLikeListByUser= new LikeLessons();
                lessonLikeListByUser.setLesson_no(lesson.getLessonNo());
                lessonLikeListByUser.setLesson_title(lesson.getLessonTitle());
                lessonLikeListByUser.setLesson_sname(lesson.getLessonSname());
                lessonLikeListByUser.setLesson_oname(lesson.getLessonOname());
                lessons.add(lessonLikeListByUser);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetLessonLikesResponseDto.success(lessons);
    }

    @Override
    public ResponseEntity<? super GetRecipeLikesResponseDto> getRecipeLikes(String userId) {

        List<LikeRecipes> likeRecipes = new ArrayList<>();

        try {

            User user = userRepository.findByUserId(userId);
            Long userNo = user.getUserNo();
            List<RecipeLike> recipeLikes = reviewLikeRepository.findAllByUser_UserNo(userNo);
            if(recipeLikes == null) GetRecipeLikesResponseDto.noExistRecipe();

            List<Long> recipeNos = new ArrayList<>();
            for (RecipeLike list : recipeLikes) {
                recipeNos.add(list.getRecipe().getRecipeNo());
            }

            List<Recipe> recipes = recipeRepository.findAllByRecipeNoIn(recipeNos);
            for (Recipe recipe : recipes){
                LikeRecipes likeRecipesByUser = new LikeRecipes();
                likeRecipesByUser.setRecipe_no(recipe.getRecipeNo());
                likeRecipesByUser.setRecipe_title(recipe.getRecipeTitle());
                likeRecipesByUser.setRecipe_oname(recipe.getRecipeOname());
                likeRecipesByUser.setRecipe_sname(recipe.getRecipeSname());
                likeRecipes.add(likeRecipesByUser);
            }


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetRecipeLikesResponseDto.success(likeRecipes);
    }


}
