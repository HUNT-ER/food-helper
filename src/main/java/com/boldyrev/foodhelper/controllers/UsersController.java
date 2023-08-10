package com.boldyrev.foodhelper.controllers;

import com.boldyrev.foodhelper.dto.RecipeDTO;
import com.boldyrev.foodhelper.services.RecipesService;
import com.boldyrev.foodhelper.services.UsersService;
import com.boldyrev.foodhelper.util.mappers.RecipeMapper;
import com.boldyrev.foodhelper.util.mappers.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;
    private final UserMapper userMapper;
    private final RecipesService recipesService;
    private final RecipeMapper recipeMapper;

    public UsersController(UsersService usersService, UserMapper userMapper,
        RecipesService recipesService, RecipeMapper recipeMapper) {
        this.usersService = usersService;
        this.userMapper = userMapper;
        this.recipesService = recipesService;
        this.recipeMapper = recipeMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userMapper.userToUserDTO(usersService.findById(id)));
    }

    @GetMapping("/{id}/recipes")
    public ResponseEntity<?> getRecipes(@PathVariable("id") Integer id,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Page<RecipeDTO> recipes = recipesService.findAllByUserId(id, page, size)
            .map(recipeMapper::recipeToRecipeDTO);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(recipes);
    }

}
