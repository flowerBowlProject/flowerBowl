package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.*;
import com.flowerbowl.web.dto.object.LikeLessonList;
import com.flowerbowl.web.dto.object.LikeRecipeList;
import com.flowerbowl.web.dto.response.ResponseDto;
import com.flowerbowl.web.dto.response.mypage.GetLessonLikeListResponseDto;
import com.flowerbowl.web.dto.response.mypage.GetRecipeLikeListResponseDto;
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
    private final RecipeRepository recipeRepository;
    private final RecipeLikeRepository recipeLikeRepository;

    /**
     * 개인적으로 로직이 너무 아쉬움 view 테이블로 하면 좋을 거 같음 나중에 리팩토링해보자
     */
    @Override
    public ResponseEntity<? super GetLessonLikeListResponseDto> getLessonLikeList(String userId) {

        List<LikeLessonList> lessons = new ArrayList<>();

        try {
            User user = userRepository.findByUserId(userId);
            Long userNo = user.getUserNo();
            List<LessonLike> lessonLikeList = lessonLikeRepository.findAllByUser_UserNo(userNo);
            if (lessonLikeList == null) {
                GetLessonLikeListResponseDto.noExistLesson();}


            List<Long> lessonNos = new ArrayList<>();
            for (LessonLike list : lessonLikeList) {
                lessonNos.add(list.getLesson().getLessonNo());
            }

            List<Lesson> lessonList = lessonRepository.findAllByLessonNoIn(lessonNos);
            for(Lesson lesson : lessonList){
                LikeLessonList lessonLikeListByUser= new LikeLessonList();
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

        return GetLessonLikeListResponseDto.success(lessons);
    }

    @Override
    public ResponseEntity<? super GetRecipeLikeListResponseDto> getRecipeLikeList(String userId) {

        List<LikeRecipeList> recipes = new ArrayList<>();

        try {

            User user = userRepository.findByUserId(userId);
            Long userNo = user.getUserNo();
            List<RecipeLike> recipeLikeList = recipeLikeRepository.findAllByUser_UserNo(userNo);
            if (recipeLikeList == null) {GetRecipeLikeListResponseDto.noExistRecipe();}

            List<Long> recipeNos = new ArrayList<>();
            for (RecipeLike list: recipeLikeList) {
                recipeNos.add(list.getRecipe().getRecipeNo());
            }

            List<Recipe> recipeList = recipeRepository.findAllByRecipeNoIn(recipeNos);
            for (Recipe recipe: recipeList) {
                LikeRecipeList recipeLikeListByUser = new LikeRecipeList();
                recipeLikeListByUser.setRecipe_no(recipe.getRecipeNo());
                recipeLikeListByUser.setRecipe_title(recipe.getRecipeTitle());
                recipeLikeListByUser.setRecipe_oname(recipe.getRecipeOname());
                recipeLikeListByUser.setRecipe_sname(recipe.getRecipeSname());
                recipes.add(recipeLikeListByUser);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetRecipeLikeListResponseDto.success(recipes);
    }
}
