package com.boldyrev.foodhelper.controllers;

import com.boldyrev.foodhelper.controllers.responses.CustomResponse;
import com.boldyrev.foodhelper.dto.IngredientCategoryDTO;
import com.boldyrev.foodhelper.dto.transfer.Exist;
import com.boldyrev.foodhelper.dto.transfer.New;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.services.IngredientCategoriesService;
import com.boldyrev.foodhelper.util.mappers.IngredientCategoryMapper;
import com.boldyrev.foodhelper.util.validators.IngredientCategoryValidator;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/ingredient-categories")
@Validated
public class IngredientCategoriesController {

    private final IngredientCategoriesService categoriesService;

    private final IngredientCategoryMapper categoryMapper;

    private final IngredientCategoryValidator validator;

    @Autowired
    public IngredientCategoriesController(IngredientCategoriesService categoriesService,
        IngredientCategoryMapper categoryMapper, IngredientCategoryValidator validator) {
        this.categoriesService = categoriesService;
        this.categoryMapper = categoryMapper;
        this.validator = validator;
    }

    //todo вывести полное дерево
    @GetMapping
    public ResponseEntity<CustomResponse> getAll() {
        List<IngredientCategoryDTO> categories = categoriesService.findAll().stream()
            .map(categoryMapper::categoryToCategoryDTO).toList();

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(categories)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getById(@PathVariable(name = "id") @Min(1) Integer id) {

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(categoryMapper.categoryToCategoryDTO(categoriesService.findById(id)))
                .build());
    }

    @PostMapping
    public ResponseEntity<CustomResponse> create(
        @RequestBody @Validated(New.class) IngredientCategoryDTO categoryDTO, BindingResult errors) {
        validator.validate(categoryDTO, errors);
        IngredientCategory category = categoryMapper.categoryDTOtoCategory(categoryDTO);

        return new ResponseEntity<>(CustomResponse.builder()
            .httpStatus(HttpStatus.CREATED)
            .body(categoryMapper.categoryToCategoryDTO(categoriesService.save(category)))
            .build(), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse> editById(@PathVariable("id") @Min(1) Integer id,
        @RequestBody @Validated(Exist.class) IngredientCategoryDTO categoryDTO, BindingResult errors) {
        validator.validate(categoryDTO, errors);
        IngredientCategory category = categoryMapper.categoryDTOtoCategory(categoryDTO);
        categoriesService.update(id, category);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
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
                .message(String.format("Ingredient category with id=%d was deleted or not exists", id))
                .build());
    }

}
