package com.boldyrev.foodhelper.controllers;

import com.boldyrev.foodhelper.controllers.responses.CustomResponse;
import com.boldyrev.foodhelper.dto.IngredientCategoryDTO;
import com.boldyrev.foodhelper.dto.IngredientDTO;
import com.boldyrev.foodhelper.dto.transfer.NewCategory;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.services.IngredientCategoriesService;
import com.boldyrev.foodhelper.services.IngredientsService;
import com.boldyrev.foodhelper.util.mappers.IngredientCategoryMapper;
import com.boldyrev.foodhelper.util.mappers.IngredientMapper;
import com.boldyrev.foodhelper.util.validators.IngredientCategoryValidator;
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
@RequestMapping("/api/v1/ingredient-categories")
@Validated
public class IngredientCategoriesController {

    private final IngredientCategoriesService categoriesService;
    private final IngredientCategoryMapper categoryMapper;
    private final IngredientCategoryValidator validator;
    private final IngredientsService ingredientsService;
    private final IngredientMapper ingredientMapper;

    @Autowired
    public IngredientCategoriesController(IngredientCategoriesService categoriesService,
        IngredientCategoryMapper categoryMapper, IngredientCategoryValidator validator,
        IngredientsService ingredientsService, IngredientMapper ingredientMapper) {
        this.categoriesService = categoriesService;
        this.categoryMapper = categoryMapper;
        this.validator = validator;
        this.ingredientsService = ingredientsService;
        this.ingredientMapper = ingredientMapper;
    }

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


    @GetMapping("/{id}/ingredients")
    public ResponseEntity<Page<IngredientDTO>> getIngredients(@PathVariable("id") Integer id,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size) {

        Page<IngredientDTO> ingredients = ingredientsService.findAllByCategoryId(id, page, size)
            .map(ingredientMapper::ingredientToIngredientDTO);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(ingredients);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> create(
        @RequestBody @Validated(NewCategory.class) IngredientCategoryDTO categoryDTO,
        BindingResult errors) {
        validator.validate(categoryDTO, errors);
        IngredientCategory category = categoryMapper.categoryDTOtoCategory(categoryDTO);

        return new ResponseEntity<>(CustomResponse.builder()
            .httpStatus(HttpStatus.CREATED)
            .body(categoryMapper.categoryToCategoryDTO(categoriesService.save(category)))
            .build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> editById(@PathVariable("id") @Min(1) Integer id,
        @RequestBody @Validated(NewCategory.class) IngredientCategoryDTO categoryDTO,
        BindingResult errors) {
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
                .message(
                    String.format("Ingredient category with id=%d was deleted or not exists", id))
                .build());
    }

}
