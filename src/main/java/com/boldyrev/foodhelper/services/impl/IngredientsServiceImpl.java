package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.repositories.IngredientsRepository;
import com.boldyrev.foodhelper.services.IngredientsService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final IngredientsRepository ingredientsRepository;

    @Autowired
    public IngredientsServiceImpl(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Ingredient findById(int id) {
        log.debug("Getting ingredient with id={}", id);
        return ingredientsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("Ingredient with id=%d not found.", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Ingredient findByName(String name) {
        log.debug("Getting ingredient with name={}", name);
        return ingredientsRepository.findByNameIgnoreCase(name)
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("Ingredient with name=%s not found.", name)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ingredient> findAll() {
        log.debug("Getting all ingredients");

        List<Ingredient> ingredients = ingredientsRepository.findAll();

        if (ingredients.isEmpty()) {
            log.debug("Ingredients not found");
            throw new EmptyDataException("Ingredients not found");
        }

        return ingredients;
    }

    @Override
    @Transactional
    public Ingredient save(Ingredient ingredient) {

        log.debug("Saving ingredient with name={}", ingredient.getName());
        return ingredientsRepository.save(ingredient);
    }

    @Override
    @Transactional
    public void update(int id, Ingredient ingredient) {

        Ingredient foundIngredient = this.findById(id);

        log.debug("Updating ingredient with id={} and name={}", foundIngredient.getId(),
            foundIngredient.getName());

        foundIngredient.setName(ingredient.getName());
        foundIngredient.setCategory(ingredient.getCategory());
        foundIngredient.setRecipes(ingredient.getRecipes());
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Deleting ingredient with id={}", id);
        ingredientsRepository.deleteById(id);
    }
}
