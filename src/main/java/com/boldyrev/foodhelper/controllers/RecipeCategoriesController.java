package com.boldyrev.foodhelper.controllers;

import com.boldyrev.foodhelper.controllers.responses.CustomResponse;
import com.boldyrev.foodhelper.dto.RecipeCategoryDTO;
import com.boldyrev.foodhelper.dto.transfer.Exist;
import com.boldyrev.foodhelper.dto.transfer.New;
import com.boldyrev.foodhelper.models.RecipeCategory;
import com.boldyrev.foodhelper.services.RecipeCategoriesService;
import com.boldyrev.foodhelper.util.mappers.RecipeCategoryMapper;
import com.boldyrev.foodhelper.util.validators.RecipeCategoryValidator;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipe-categories")
public class RecipeCategoriesController {

    private final RecipeCategoriesService categoriesService;

    private final RecipeCategoryMapper categoryMapper;

    private final RecipeCategoryValidator categoryValidator;

    @Autowired
    public RecipeCategoriesController(RecipeCategoriesService categoriesService,
        RecipeCategoryMapper categoryMapper, RecipeCategoryValidator categoryValidator) {
        this.categoriesService = categoriesService;
        this.categoryMapper = categoryMapper;
        this.categoryValidator = categoryValidator;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAll() {
        List<RecipeCategoryDTO> categories = categoriesService.findAll().stream()
            .map(categoryMapper::categoryToCategoryDTO).toList();
    //протестировать
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

    @PostMapping
    public ResponseEntity<CustomResponse> create(
        @RequestBody @Validated(New.class) RecipeCategoryDTO categoryDTO, BindingResult errors) {
        categoryValidator.validate(categoryDTO, errors);

        RecipeCategory category = categoriesService.save(
            categoryMapper.categoryDTOToCategory(categoryDTO));

        return new ResponseEntity<>(CustomResponse.builder()
            .httpStatus(HttpStatus.CREATED)
            .body(categoryMapper.categoryToCategoryDTO(category))
            .build(), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse> editById(@PathVariable("id") @Min(1) Integer id,
        @RequestBody @Validated(Exist.class) RecipeCategoryDTO categoryDTO,
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
