package com.boldyrev.foodhelper.controllers;

import com.boldyrev.foodhelper.controllers.responses.CustomResponse;
import com.boldyrev.foodhelper.dto.RecipeCategoryDTO;
import com.boldyrev.foodhelper.dto.RecipeDTO;
import com.boldyrev.foodhelper.dto.transfer.NewCategory;
import com.boldyrev.foodhelper.models.RecipeCategory;
import com.boldyrev.foodhelper.services.RecipeCategoriesService;
import com.boldyrev.foodhelper.services.RecipesService;
import com.boldyrev.foodhelper.util.mappers.RecipeCategoryMapper;
import com.boldyrev.foodhelper.util.mappers.RecipeMapper;
import com.boldyrev.foodhelper.util.validators.RecipeCategoryValidator;
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

@RestController
@RequestMapping("/api/v1/recipe-categories")
public class RecipeCategoriesController {

    private final RecipeCategoriesService categoriesService;
    private final RecipeCategoryMapper categoryMapper;
    private final RecipeCategoryValidator categoryValidator;
    private final RecipesService recipesService;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeCategoriesController(RecipeCategoriesService categoriesService,
        RecipeCategoryMapper categoryMapper, RecipeCategoryValidator categoryValidator,
        RecipesService recipesService, RecipeMapper recipeMapper) {
        this.categoriesService = categoriesService;
        this.categoryMapper = categoryMapper;
        this.categoryValidator = categoryValidator;
        this.recipesService = recipesService;
        this.recipeMapper = recipeMapper;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAll() {
        List<RecipeCategoryDTO> categories = categoriesService.findAll().stream()
            .map(categoryMapper::categoryToCategoryDTO).toList();

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(categories)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getById(@PathVariable("id") @Min(1) Integer id) {

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(categoryMapper.categoryToCategoryDTO(categoriesService.findById(id)))
                .build());
    }

    @GetMapping("/{id}/recipes")
    public ResponseEntity<Page<RecipeDTO>> getRecipes(@PathVariable("id") Integer id,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size) {

        Page<RecipeDTO> recipes = recipesService.findAllByCategoryId(id, page, size)
            .map(recipeMapper::recipeToRecipeDTO);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(recipes);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> create(
        @RequestBody @Validated(NewCategory.class) RecipeCategoryDTO categoryDTO,
        BindingResult errors) {
        categoryValidator.validate(categoryDTO, errors);

        RecipeCategory category = categoriesService.save(
            categoryMapper.categoryDTOToCategory(categoryDTO));

        return new ResponseEntity<>(CustomResponse.builder()
            .httpStatus(HttpStatus.CREATED)
            .body(categoryMapper.categoryToCategoryDTO(category))
            .build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> editById(@PathVariable("id") @Min(1) Integer id,
        @RequestBody @Validated(NewCategory.class) RecipeCategoryDTO categoryDTO,
        BindingResult errors) {
        categoryValidator.validate(categoryDTO, errors);

        categoriesService.update(id, categoryMapper.categoryDTOToCategory(categoryDTO));

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(categoryDTO)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteById(@PathVariable("id") @Min(1) Integer id) {
        categoriesService.delete(id);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Recipe category with id=%d was deleted or not exists", id))
                .build());
    }
}
