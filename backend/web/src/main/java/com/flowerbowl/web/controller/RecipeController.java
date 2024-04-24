package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.request.recipe.CrRecipeReqDto;
import com.flowerbowl.web.dto.request.recipe.UpRecipeReqDto;
import com.flowerbowl.web.dto.response.recipe.RecipeResponseDto;
import com.flowerbowl.web.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("")
    private ResponseEntity<? extends RecipeResponseDto> createRecipe(@RequestBody CrRecipeReqDto request, @AuthenticationPrincipal String userId) throws Exception {
        return recipeService.createRecipe(request, userId);
    }

    @PutMapping("/{recipe_no}")
    private ResponseEntity<? extends RecipeResponseDto> updateRecipe(@RequestBody UpRecipeReqDto request, @PathVariable Long recipe_no, @AuthenticationPrincipal String userId) throws Exception {
        return recipeService.updateRecipe(request, recipe_no, userId);
    }

    @DeleteMapping("/{recipe_no}")
    private ResponseEntity<? extends RecipeResponseDto> deleteRecipe(@PathVariable Long recipe_no, @AuthenticationPrincipal String userId) throws Exception {
        return recipeService.deleteRecipe(recipe_no, userId);
    }

    @GetMapping("/guest")
    private ResponseEntity<? extends RecipeResponseDto> getAllRecipesGuest() throws Exception {
        return recipeService.getAllRecipesGuest();
    }

    @GetMapping("")
    private ResponseEntity<? extends RecipeResponseDto> getAllRecipes(@AuthenticationPrincipal String userId) throws Exception {
        return recipeService.getAllRecipes(userId);
    }

    @GetMapping("/guest/{recipe_no}")
    private ResponseEntity<? extends RecipeResponseDto> getRecipeGuest(@PathVariable Long recipe_no) throws Exception {
        return recipeService.getRecipeGuest(recipe_no);
    }

    @GetMapping("/{recipe_no}")
    private ResponseEntity<? extends RecipeResponseDto> getRecipe(@PathVariable Long recipe_no, @AuthenticationPrincipal String userId) throws Exception {
        return recipeService.getRecipe(recipe_no, userId);
    }

    @GetMapping("/guest/list")
    private ResponseEntity<? extends RecipeResponseDto> getRecipesCategoryGuest(@RequestParam("category") String koreanName) throws Exception {
        return recipeService.getRecipesCategoryGuest(koreanName);
    }

    @GetMapping("/list")
    private ResponseEntity<? extends RecipeResponseDto> getRecipesCategory(@RequestParam("category") String koreanName, @AuthenticationPrincipal String userId) throws Exception {
        return recipeService.getRecipesCategory(koreanName, userId);
    }

    @PostMapping("/like/{recipe_no}")
    private ResponseEntity<? extends RecipeResponseDto> likeRecipe(@PathVariable Long recipe_no, @AuthenticationPrincipal String userId) throws Exception {
        return recipeService.updateRecipeLike(recipe_no, userId);
    }

}
