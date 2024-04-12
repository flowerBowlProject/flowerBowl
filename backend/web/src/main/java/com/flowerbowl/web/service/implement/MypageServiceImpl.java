package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.*;
import com.flowerbowl.web.dto.object.mypage.*;
import com.flowerbowl.web.dto.response.ResponseDto;
import com.flowerbowl.web.dto.response.mypage.*;
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
            if (lessonLikeList == null) {
                GetLessonLikesResponseDto.noExistLesson();
            }


            List<Long> lessonNos = new ArrayList<>();
            for (LessonLike list : lessonLikeList) {
                lessonNos.add(list.getLesson().getLessonNo());
            }

            List<Lesson> lessonList = lessonRepository.findAllByLessonNoIn(lessonNos);
            for (Lesson lesson : lessonList) {
                LikeLessons lessonLikeListByUser = new LikeLessons();
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
            if (recipeLikes == null) GetRecipeLikesResponseDto.noExistRecipe();

            List<Long> recipeNos = new ArrayList<>();
            for (RecipeLike list : recipeLikes) {
                recipeNos.add(list.getRecipe().getRecipeNo());
            }

            List<Recipe> recipes = recipeRepository.findAllByRecipeNoIn(recipeNos);
            for (Recipe recipe : recipes) {
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

    @Override
    public ResponseEntity<? super GetPayLessonResponseDto> getPayLessons(String userId) {

        List<PayLessons> payLessons = new ArrayList<>();

        try {

            List<Object[]> dbResult = userRepository.findAllPayLesson(userId);
            for (Object[] posts : dbResult) {
                PayLessons lessons = new PayLessons();
                lessons.setPay_date((String) posts[0]);
                lessons.setLesson_title((String) posts[1]);
                lessons.setLesson_writer((String) posts[2]);
                lessons.setReview_score((Long) posts[3]);
                payLessons.add(lessons);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetPayLessonResponseDto.success(payLessons);
    }

    @Override
    public ResponseEntity<? super GetMyRecipeResponseDto> getMyRecipes(String userId) {

        List<MyRecipes> myRecipes = new ArrayList<>();

        try {

            List<Object[]> dbResult = userRepository.findAllRecipeByUser(userId);
            for (Object[] posts : dbResult) {
                MyRecipes myRecipe = new MyRecipes();
                myRecipe.setRecipe_date((String) posts[0]);
                myRecipe.setRecipe_title((String) posts[1]);
                myRecipe.setBookmark_cnt((Long) posts[2]);
                myRecipe.setComment_cnt((Long) posts[3]);
                myRecipes.add(myRecipe);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }


        return GetMyRecipeResponseDto.success(myRecipes);
    }

    @Override
    public ResponseEntity<? super GetMyLessonResponseDto> getMyLessons(String userId) {

        List<MyLessons> myLessons = new ArrayList<>();

        try {

            List<Object[]> dbResult = userRepository.findAllLessonByUser(userId);
            for (Object[] posts : dbResult) {
                MyLessons myLesson = new MyLessons();
                myLesson.setLesson_date((String) posts[0]);
                myLesson.setLesson_title((String) posts[1]);
                myLesson.setBookmark_cnt((Long) posts[2]);
                myLesson.setReview_cnt((Long) posts[3]);
                myLessons.add(myLesson);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetMyLessonResponseDto.success(myLessons);
    }

    @Override
    public ResponseEntity<? super GetPurchasersResponseDto> getPurchasers(String userId) {

        List<PurchaserList> purchasers = new ArrayList<>();

        try {

            List<Object[]> dbResult = userRepository.findPurchaser(userId);
            for (Object[] posts : dbResult) {
                PurchaserList purchaser = new PurchaserList();
                purchaser.setPay_date((String) posts[0]);
                purchaser.setUser_nickname((String) posts[1]);
                purchaser.setUser_phone((String) posts[2]);
                purchaser.setLesson_title((String) posts[3]);
                purchasers.add(purchaser);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetPurchasersResponseDto.success(purchasers);
    }

    @Override
    public ResponseEntity<? super GetPaysResponseDto> getPays(String userId) {

        List<Pays> pays = new ArrayList<>();

        try {

            List<Object[]> dbResult = userRepository.findPaysByUserId(userId);
            for (Object[] posts : dbResult) {
                Pays pay = new Pays();
                pay.setPay_date((String) posts[0]);
                pay.setPay_price((String) posts[1]);
                pay.setLesson_title((String) posts[2]);
                pay.setLesson_writer((String) posts[3]);
                pays.add(pay);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetPaysResponseDto.success(pays);
    }

    @Override
    public ResponseEntity<? super DeletePayByUserResponseDto> deletePayByUser(String userId, Long payNo) {

        try {

            int dbResult = userRepository.deletePayByUser(userId, payNo);
            if(dbResult == 0) return DeletePayByUserResponseDto.notExistPayNo();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeletePayByUserResponseDto.success();
    }

    @Override
    public ResponseEntity<? super DeletePayByChefResponseDto> deletePayByChef(String userId, Long payNo) {

        try {


            int dbResult = userRepository.deletePayByChef(userId, payNo);
            if(dbResult == 0) return DeletePayByChefResponseDto.notExistPayNo();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }


        return DeletePayByChefResponseDto.success();
    }


}
