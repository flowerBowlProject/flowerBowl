package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.recipe.CrRecipeReqDto;
import com.flowerbowl.web.dto.request.recipe.UpRecipeReqDto;
import com.flowerbowl.web.dto.response.recipe.RecipeResponseDto;
import org.springframework.http.ResponseEntity;

public interface RecipeService {

    public ResponseEntity<? extends RecipeResponseDto> createRecipe(CrRecipeReqDto request, String userId) throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> updateRecipe(UpRecipeReqDto reqeust, Long recipe_no, String userId) throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> deleteRecipe(Long recipe_no, String userId) throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> getAllRecipesGuest() throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> getAllRecipes(String userId) throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> getRecipeGuest(Long recipe_no) throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> getRecipe(Long recipe_no, String userId) throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> getRecipesCategoryGuest(String koreanName) throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> getRecipesCategory(String koreanName, String userId) throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> getRecipesAdmin() throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> getRecipesPopular() throws Exception;

    public ResponseEntity<? extends RecipeResponseDto> updateRecipeLike(Long recipe_no, String userId) throws Exception;

}
