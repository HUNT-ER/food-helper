package com.boldyrev.foodhelper.util.validators;

import com.boldyrev.foodhelper.dto.IngredientCategoryDTO;
import com.boldyrev.foodhelper.exceptions.ValidationException;
import com.boldyrev.foodhelper.repositories.IngredientCategoriesRepository;
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

        log.debug("Errors: {}", errors.hasErrors());
        log.debug(((IngredientCategoryDTO) target).toString());

        if (errors.hasErrors()) {
            throw new ValidationException(getErrors(errors));
        }

    }

}
