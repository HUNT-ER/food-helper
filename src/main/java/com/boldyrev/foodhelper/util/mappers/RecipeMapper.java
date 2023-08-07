package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.RecipeCategoryDTO;
import com.boldyrev.foodhelper.dto.RecipeDTO;
import com.boldyrev.foodhelper.models.Recipe;
import com.boldyrev.foodhelper.models.RecipeCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Collectors.class})
public abstract class RecipeMapper {

    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected RecipeCategoryMapper categoryMapper;
    @Autowired
    protected RecipeIngredientMapper recipeIngredientMapper;

    @Mapping(target = "creator", expression = "java(userMapper.userDTOToUser(recipe.getCreator()))")
    @Mapping(target = "category", expression = "java(categoryMapper.categoryDTOToCategory(recipe.getCategory()))")
    @Mapping(target = "recipeIngredients", expression = "java(recipe.getRecipeIngredients().stream().map(r -> recipeIngredientMapper.dtoToRecipeIngredient(r)).collect(Collectors.toSet()))")
    public abstract Recipe recipeDTOToRecipe(RecipeDTO recipe);

    @Mapping(target = "creator", expression = "java(userMapper.userToUserDTO(recipe.getCreator()))")
    @Mapping(target = "category", expression = "java(categoryMapper.categoryToCategoryDTO(recipe.getCategory()))")
    @Mapping(target = "recipeIngredients", expression = "java(recipe.getRecipeIngredients().stream().map(r -> recipeIngredientMapper.recipeIngredientToDTO(r)).collect(Collectors.toSet()))")
    public abstract RecipeDTO recipeToRecipeDTO(Recipe recipe);


}
