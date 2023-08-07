package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.IngredientDTO;
import com.boldyrev.foodhelper.dto.RecipeIngredientDTO;
import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.models.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RecipeIngredientMapper {


    //@Mapping(target = "ingredient", expression = "java(ingredientMapper.ingredientToIngredientDTO(recipeIngredient.getIngredient()))")
    public abstract RecipeIngredientDTO recipeIngredientToDTO(RecipeIngredient recipeIngredient);

    //@Mapping(target = "ingredient", expression = "java(ingredientMapper.ingredientDTOToIngredient(recipeIngredient.getIngredient()))")
    public abstract RecipeIngredient dtoToRecipeIngredient(RecipeIngredientDTO recipeIngredient);

    @Mapping(target = "category", ignore = true)
    public abstract IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

    @Mapping(target = "category", ignore = true)
    public abstract Ingredient ingredientDTOToIngredient(IngredientDTO ingredient);
}
