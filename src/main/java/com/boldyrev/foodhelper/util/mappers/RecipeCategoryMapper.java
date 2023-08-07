package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.RecipeCategoryDTO;
import com.boldyrev.foodhelper.dto.RecipeParentCategoryDTO;
import com.boldyrev.foodhelper.models.RecipeCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RecipeCategoryMapper {

    public abstract RecipeCategoryDTO categoryToCategoryDTO(RecipeCategory category);

    public abstract RecipeCategory categoryDTOToCategory(RecipeCategoryDTO category);

    public abstract RecipeParentCategoryDTO parentCategoryToDTO(
        RecipeCategory parentCategory);

    public abstract RecipeCategory dtoToParentCategory(
        RecipeParentCategoryDTO parentCategory);
}
