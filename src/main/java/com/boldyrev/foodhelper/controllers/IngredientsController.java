package com.boldyrev.foodhelper.controllers;

import com.boldyrev.foodhelper.controllers.responses.CustomResponse;
import com.boldyrev.foodhelper.dto.IngredientDTO;
import com.boldyrev.foodhelper.dto.transfer.NewIngredient;
import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.services.IngredientsService;
import com.boldyrev.foodhelper.util.mappers.IngredientMapper;
import com.boldyrev.foodhelper.util.validators.IngredientValidator;
import jakarta.validation.constraints.Min;
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
@RequestMapping("/api/v1/ingredients")
public class IngredientsController {

    private final IngredientsService ingredientsService;

    private final IngredientMapper ingredientMapper;

    private final IngredientValidator ingredientValidator;

    @Autowired
    public IngredientsController(IngredientsService ingredientsService,
        IngredientMapper ingredientMapper, IngredientValidator ingredientValidator) {
        this.ingredientsService = ingredientsService;
        this.ingredientMapper = ingredientMapper;
        this.ingredientValidator = ingredientValidator;
    }

    //todo add pagination and sorting
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") Integer size) {
        Page<IngredientDTO> ingredients = ingredientsService.findAll(page, size)
            .map(ingredientMapper::ingredientToIngredientDTO);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(ingredients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @Min(1) Integer id) {

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON).body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(ingredientMapper.ingredientToIngredientDTO(ingredientsService.findById(id)))
                .build());
    }



    @PostMapping
    public ResponseEntity<?> create(
        @RequestBody @Validated(NewIngredient.class) IngredientDTO ingredientDTO,
        BindingResult errors) {
        ingredientValidator.validate(ingredientDTO, errors);

        Ingredient ingredient = ingredientsService.save(
            ingredientMapper.ingredientDTOToIngredient(ingredientDTO));

        return new ResponseEntity<>(CustomResponse.builder()
            .httpStatus(HttpStatus.CREATED)
            .body(ingredientMapper.ingredientToIngredientDTO(ingredient))
            .build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable("id") @Min(1) Integer id,
        @RequestBody @Validated(NewIngredient.class) IngredientDTO ingredientDTO,
        BindingResult errors) {
        ingredientValidator.validate(ingredientDTO, errors);

        ingredientsService.update(id, ingredientMapper.ingredientDTOToIngredient(ingredientDTO));

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .body(ingredientDTO)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") @Min(1) Integer id) {
        ingredientsService.delete(id);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CustomResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Ingredient with id=%d was deleted or not exists", id))
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam(value = "name") String name,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") Integer size) {
        Page<IngredientDTO> ingredients = ingredientsService.searchByName(name, page, size)
            .map(ingredientMapper::ingredientToIngredientDTO);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(ingredients);
    }
}
