package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.IngredientCategoryDTO;
import com.boldyrev.foodhelper.dto.IngredientParentCategoryDTO;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.services.IngredientCategoriesService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper(componentModel = "spring")
public abstract class IngredientCategoryMapper {

    public abstract IngredientCategoryDTO categoryToCategoryDTO(
        IngredientCategory category);

    public abstract IngredientCategory categoryDTOtoCategory(
        IngredientCategoryDTO categoryDTO);

    public abstract IngredientParentCategoryDTO parentCategoryToDTO(IngredientCategory parentCategory);
    public abstract IngredientCategory dtoToParentCategory(IngredientParentCategoryDTO parentCategory);
}
