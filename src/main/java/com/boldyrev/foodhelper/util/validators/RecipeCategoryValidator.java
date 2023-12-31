package com.boldyrev.foodhelper.util.validators;

import com.boldyrev.foodhelper.dto.RecipeCategoryDTO;
import com.boldyrev.foodhelper.exceptions.ValidationException;
import com.boldyrev.foodhelper.repositories.RecipeCategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class RecipeCategoryValidator extends CustomValidator {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final RecipeCategoriesRepository categoriesRepository;

    @Autowired
    public RecipeCategoryValidator(RecipeCategoriesRepository categoriesRepository) {
        super("Recipe category");
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RecipeCategoryDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        log.debug("Errors: {}", errors.hasErrors());
        log.debug(((RecipeCategoryDTO) target).toString());

        if (errors.hasErrors()) {
            throw new ValidationException(getErrors(errors));
        }
    }
}
