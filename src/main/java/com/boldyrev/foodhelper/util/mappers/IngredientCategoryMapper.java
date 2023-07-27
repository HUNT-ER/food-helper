package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.IngredientCategoryDTO;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.services.IngredientCategoriesService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper(componentModel = "spring")
public abstract class IngredientCategoryMapper {

    @Autowired
    protected IngredientCategoriesService categoriesService;

    @Mapping(target = "parentCategory", expression = "java(category.getParentCategory() == null ? null : category.getParentCategory().getName())")
    public abstract IngredientCategoryDTO categoryToCategoryDTO(
        IngredientCategory category);

    @Mapping(target = "parentCategory", expression = "java(categoryDTO.getParentCategory() == null ? null : categoriesService.findByName(categoryDTO.getParentCategory()))")
    public abstract IngredientCategory categoryDTOtoCategory(
        IngredientCategoryDTO categoryDTO);
}
