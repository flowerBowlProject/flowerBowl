package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.recipe.request.CrRecipeReqDto;
import com.flowerbowl.web.dto.recipe.request.UpRecipeReqDto;
import com.flowerbowl.web.dto.recipe.response.ResponseDto;
import com.flowerbowl.web.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("")
    private ResponseEntity<? extends ResponseDto> createRecipe(@RequestBody CrRecipeReqDto request) throws Exception {
        return recipeService.createRecipe(request);
    }

    @PutMapping("/{recipe_no}")
    private ResponseEntity<? extends ResponseDto> updateRecipe(@RequestBody UpRecipeReqDto request, @PathVariable Long recipe_no) throws Exception {
        return recipeService.updateRecipe(request, recipe_no);
    }

    @DeleteMapping("/{recipe_no}")
    private ResponseEntity<? extends ResponseDto> deleteRecipe(@PathVariable Long recipe_no) throws Exception {
        return recipeService.deleteRecipe(recipe_no);
    }

    @GetMapping("/guest")
    private ResponseEntity<? extends ResponseDto> getAllRecipesGuest() throws Exception {
        return recipeService.getAllRecipesGuest();
    }

    @GetMapping("")
    private ResponseEntity<? extends ResponseDto> getAllRecipes() throws Exception {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/guest/{recipe_no}")
    private ResponseEntity<? extends ResponseDto> getRecipeGuest(@PathVariable Long recipe_no) throws Exception {
        return recipeService.getRecipeGuest(recipe_no);
    }

    @GetMapping("/{recipe_no}")
    private ResponseEntity<? extends ResponseDto> getRecipe(@PathVariable Long recipe_no) throws Exception {
        return recipeService.getRecipe(recipe_no);
    }

    @PostMapping("/like/{recipe_no}")
    private ResponseEntity<? extends ResponseDto> likeRecipe(@PathVariable Long recipe_no) throws Exception {
        return recipeService.updateRecipeLike(recipe_no);
    }

}
