package com.boldyrev.foodhelper.util.validators;

import com.boldyrev.foodhelper.dto.IngredientCategoryDTO;
import com.boldyrev.foodhelper.exceptions.EntityAlreadyExistsException;
import com.boldyrev.foodhelper.exceptions.ValidationException;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.repositories.IngredientCategoriesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class IngredientCategoryValidator extends CustomValidator {

    private final IngredientCategoriesRepository categoriesRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public IngredientCategoryValidator(IngredientCategoriesRepository categoriesRepository) {
        super("Ingredient category");
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(IngredientCategoryDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IngredientCategoryDTO category = (IngredientCategoryDTO) target;

        Optional<IngredientCategory> foundCategory = categoriesRepository.findByNameIgnoreCase(
            category.getName());

        if (foundCategory.isPresent()) {
            throw new EntityAlreadyExistsException(
                String.format("Category with name '%s' already exists", category.getName()));
        }

        if (category.getParentCategory() != null && categoriesRepository.findByNameIgnoreCase(
            category.getParentCategory()).isEmpty()) {
            errors.rejectValue("parentCategory", "parent_category_not_exists",
                String.format("Category with name '%s' not exists an can't be parent",
                    category.getParentCategory()));
        }

        if (errors.hasErrors()) {
            throw new ValidationException(getErrors(errors));
        }

    }


}
