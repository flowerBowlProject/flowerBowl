package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.recipe.request.CrRecipeReqDto;
import com.flowerbowl.web.dto.recipe.request.UpRecipeReqDto;
import com.flowerbowl.web.dto.recipe.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface RecipeService {

    public ResponseEntity<? extends ResponseDto> createRecipe(CrRecipeReqDto request) throws Exception;

    public ResponseEntity<? extends ResponseDto> updateRecipe(UpRecipeReqDto reqeust, Long recipe_no) throws Exception;

    public ResponseEntity<? extends ResponseDto> deleteRecipe(Long recipe_no) throws Exception;

    public ResponseEntity<? extends ResponseDto> getAllRecipesGuest() throws Exception;

    public ResponseEntity<? extends ResponseDto> getAllRecipes() throws Exception;

    public ResponseEntity<? extends ResponseDto> getRecipeGuest(Long recipe_no) throws Exception;

    public ResponseEntity<? extends ResponseDto> getRecipe(Long recipe_no) throws Exception;

}
