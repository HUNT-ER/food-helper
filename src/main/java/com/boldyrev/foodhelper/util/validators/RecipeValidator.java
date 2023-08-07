package com.boldyrev.foodhelper.util.validators;

import com.boldyrev.foodhelper.dto.RecipeDTO;
import com.boldyrev.foodhelper.exceptions.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class RecipeValidator extends CustomValidator {


    protected RecipeValidator() {
        super("Recipe");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RecipeDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (errors.hasErrors()) {
            throw new ValidationException(getErrors(errors));
        }
    }
}
