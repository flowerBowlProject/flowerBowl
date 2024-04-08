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

    @PutMapping("/{no}")
    private ResponseEntity<? extends ResponseDto> updateRecipe(@RequestBody UpRecipeReqDto request, @PathVariable Long no) throws Exception {
        return recipeService.updateRecipe(request, no);
    }

    @DeleteMapping("/{no}")
    private ResponseEntity<? extends ResponseDto> deleteRecipe(@PathVariable Long no) throws Exception {
        return recipeService.deleteRecipe(no);
    }

    @GetMapping("/guest")
    private ResponseEntity<? extends ResponseDto> getAllRecipesGuest() throws Exception {
        return recipeService.getAllRecipesGuest();
    }

    @GetMapping("")
    private ResponseEntity<? extends ResponseDto> getAllRecipes() throws Exception {
        return recipeService.getAllRecipes();
    }

}
