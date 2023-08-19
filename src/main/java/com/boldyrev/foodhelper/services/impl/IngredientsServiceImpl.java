package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.repositories.IngredientsRepository;
import com.boldyrev.foodhelper.services.IngredientsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return ingredientsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("Ingredient with id=%d not found.", id)));
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Ingredient> findAll(int page, int size) {
        Page<Ingredient> ingredients = ingredientsRepository.findAll(
            PageRequest.of(page, size, Sort.by("name")));

        if (ingredients.isEmpty()) {
            log.debug("Ingredients not found");
            throw new EmptyDataException("Ingredients not found");
        }

        return ingredients;
    }

    @Override
    @Transactional
    public Ingredient save(Ingredient ingredient) {
        return ingredientsRepository.save(ingredient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ingredient> searchByName(String name, int page, int size) {
        Page<Ingredient> ingredients = ingredientsRepository.findByNameContainingIgnoreCase(name,
            PageRequest.of(page, size, Sort.by("name")));

        if (ingredients.isEmpty()) {
            throw new EmptyDataException("Ingredients not found");
        }

        return ingredients;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ingredient> findAllByCategoryId(int id, int page, int size) {
        Page<Ingredient> ingredients = ingredientsRepository.findAllByCategoryId(id,
            PageRequest.of(page, size, Sort.by("name")));

        if (ingredients.isEmpty()) {
            throw new EmptyDataException("Ingredients not found");
        }

        return ingredients;
    }

    @Override
    @Transactional
    public void update(int id, Ingredient ingredient) {
        Ingredient storedIngredient = this.findById(id);

        storedIngredient.setName(ingredient.getName());
        storedIngredient.setCategory(ingredient.getCategory());
    }

    @Override
    @Transactional
    public void delete(int id) {
        ingredientsRepository.deleteById(id);
    }
}
