package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.RecipeCategoryDTO;
import com.boldyrev.foodhelper.models.RecipeCategory;
import com.boldyrev.foodhelper.services.RecipeCategoriesService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RecipeCategoryMapper {

    @Autowired
    protected RecipeCategoriesService categoriesService;

    //добавить в маппинге проверку на null
    @Mapping(target = "parentCategory", expression = "java(category.getParentCategory() == null ? null : category.getParentCategory().getName())")
    public abstract RecipeCategoryDTO categoryToCategoryDTO(RecipeCategory category);

    @Mapping(target = "parentCategory", expression = "java(category.getParentCategory() == null ? null : categoriesService.findByName(category.getParentCategory()))")
    public abstract RecipeCategory categoryDTOToCategory(RecipeCategoryDTO category);
}
