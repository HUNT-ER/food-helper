package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.IngredientDTO;
import com.boldyrev.foodhelper.models.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class IngredientMapper {

    @Autowired
    protected IngredientCategoryMapper categoryMapper;

    @Mapping(target = "category", expression = "java(categoryMapper.categoryDTOToCategory(ingredient.getCategory()))")
    @Mapping(target = "category.parentCategory", ignore = true)
    public abstract IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

    @Mapping(target = "category", expression = "java(categoryMapper.categoryToCategoryDTO(ingredient.getCategory()))")
    @Mapping(target = "category.parentCategory", ignore = true)
    public abstract Ingredient ingredientDTOToIngredient(IngredientDTO ingredient);


}
