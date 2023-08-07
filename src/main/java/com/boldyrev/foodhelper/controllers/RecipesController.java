package com.boldyrev.foodhelper.controllers;

import com.boldyrev.foodhelper.controllers.responses.CustomResponse;
import com.boldyrev.foodhelper.dto.RecipeDTO;
import com.boldyrev.foodhelper.dto.transfer.Exist;
import com.boldyrev.foodhelper.dto.transfer.NewRecipe;
import com.boldyrev.foodhelper.services.RecipesService;
import com.boldyrev.foodhelper.util.mappers.RecipeMapper;
import com.boldyrev.foodhelper.util.validators.RecipeValidator;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

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

    //todo добавить пагинацию
    @GetMapping
    public ResponseEntity<CustomResponse> getAll() {
        List<RecipeDTO> recipeList = recipesService.findAll().stream()
            .map(recipeMapper::recipeToRecipeDTO).toList();

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(recipeList)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getById(@PathVariable("id") @Min(1) Integer id) {

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(recipeMapper.recipeToRecipeDTO(recipesService.findById(id)))
                .build());
    }

    @PostMapping
    public ResponseEntity<CustomResponse> create(
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
    public ResponseEntity<CustomResponse> editById(@PathVariable("id") @Min(1) Integer id,
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

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteById(@PathVariable("id") @Min(1) Integer id) {

        recipesService.delete(id);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Recipe with id=%d was deleted or not exists", id))
                .build());
    }
}
