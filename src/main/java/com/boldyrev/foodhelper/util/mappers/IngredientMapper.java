package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.IngredientDTO;
import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.services.IngredientCategoriesService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class IngredientMapper {

    @Autowired
    protected IngredientCategoriesService ingredientCategoriesService;

    @Mapping(target = "category", expression = "java(ingredient.getCategory().getName())")
    public abstract IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

    @Mapping(target = "category", expression = "java(ingredientCategoriesService.findByName(ingredient.getCategory()))")
    public abstract Ingredient ingredientDTOToIngredient(IngredientDTO ingredient);
}
