package com.boldyrev.foodhelper.controllers;

import com.boldyrev.foodhelper.controllers.responses.CustomResponse;
import com.boldyrev.foodhelper.dto.RecipeDTO;
import com.boldyrev.foodhelper.dto.transfer.NewRecipe;
import com.boldyrev.foodhelper.services.RecipesService;
import com.boldyrev.foodhelper.util.mappers.RecipeMapper;
import com.boldyrev.foodhelper.util.validators.RecipeValidator;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipesController {

    private final RecipesService recipesService;

    private final RecipeMapper recipeMapper;

    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipesController(RecipesService recipesService, RecipeMapper recipeMapper,
        RecipeValidator recipeValidator) {
        this.recipesService = recipesService;
        this.recipeMapper = recipeMapper;
        this.recipeValidator = recipeValidator;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") Integer size) {
        Page<RecipeDTO> recipes = recipesService.findAll(page, size)
            .map(recipeMapper::recipeToRecipeDTO);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @Min(1) Integer id) {

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(recipeMapper.recipeToRecipeDTO(recipesService.findById(id)))
                .build());
    }

    @PostMapping
    public ResponseEntity<?> create(
        @RequestBody @Validated(NewRecipe.class) RecipeDTO recipeDTO, BindingResult errors) {
        recipeValidator.validate(recipeDTO, errors);

        return new ResponseEntity<>(CustomResponse.builder()
            .httpStatus(HttpStatus.CREATED)
            .body(recipeMapper.recipeToRecipeDTO(
                recipesService.save(recipeMapper.recipeDTOToRecipe(recipeDTO))))
            .build(), HttpStatus.CREATED);
    }

    //todo переделать на пут /recipes/7, частичное обновление тоже сделать через пут, ex. /recipes/7/ingredients
    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable("id") @Min(1) Integer id,
        @RequestBody @Validated(NewRecipe.class) RecipeDTO recipeDTO, BindingResult errors) {
        recipeValidator.validate(recipeDTO, errors);

        recipesService.update(id, recipeMapper.recipeDTOToRecipe(recipeDTO));

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(recipeDTO)
                .build());
    }

    @GetMapping("{id}/image")
    public ResponseEntity<?> getImageById(@PathVariable("id") Integer id) {

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(recipeMapper.recipeToImageDTO(recipesService.getImageLinkByRecipeId(id)))
                .build());
    }


    @PutMapping("{id}/image")
    public ResponseEntity<?> editImageById(@PathVariable("id") Integer id,
        @RequestParam(name = "image", required = true) MultipartFile image) {

        recipesService.addImage(id, image);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .message(String.format("Image for recipe with id=%d was saved", id))
                .httpStatus(HttpStatus.OK)
                .build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") @Min(1) Integer id) {

        recipesService.delete(id);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Recipe with id=%d was deleted or not exists", id))
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("ingredient") List<Integer> ingredients,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") Integer size) {

        Page<RecipeDTO> recipes = recipesService.findAllByIngredientsId(ingredients, page, size)
            .map(recipeMapper::recipeToRecipeDTO);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(recipes);
    }
}
