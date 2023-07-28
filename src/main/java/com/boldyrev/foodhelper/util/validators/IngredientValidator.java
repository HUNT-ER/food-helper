package com.boldyrev.foodhelper.util.validators;

import com.boldyrev.foodhelper.dto.IngredientDTO;
import com.boldyrev.foodhelper.exceptions.EntityAlreadyExistsException;
import com.boldyrev.foodhelper.exceptions.ValidationException;
import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.repositories.IngredientCategoriesRepository;
import com.boldyrev.foodhelper.repositories.IngredientsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class IngredientValidator extends CustomValidator {

    private final IngredientsRepository ingredientsRepository;
    private final IngredientCategoriesRepository categoriesRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected IngredientValidator(IngredientsRepository ingredientsRepository,
        IngredientCategoriesRepository categoriesRepository) {
        super("Ingredient");
        this.ingredientsRepository = ingredientsRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(IngredientDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        log.debug("Errors: {}", errors.hasErrors());
        log.debug(((IngredientDTO) target).toString());

        if (!errors.hasErrors()) {

            IngredientDTO ingredient = (IngredientDTO) target;

            Optional<Ingredient> storedIngredient = ingredientsRepository.findByNameIgnoreCase(
                ingredient.getName());

            if (storedIngredient.isPresent()) {
                throw new EntityAlreadyExistsException(
                    String.format("Ingredient with name '%s' already exists", ingredient.getName()));
            }

            if (categoriesRepository.findByNameIgnoreCase(ingredient.getCategory()).isEmpty()) {
                errors.rejectValue("category", "category_not_exists",
                    String.format("Ingredient category with name='%s' not exists", ingredient.getCategory()));
            }
        }

        if (errors.hasErrors()) {
            throw new ValidationException(getErrors(errors));
        }

    }
}
