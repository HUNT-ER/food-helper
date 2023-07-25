package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.RecipeCategoryNotFoundException;
import com.boldyrev.foodhelper.models.RecipeCategory;
import com.boldyrev.foodhelper.repositories.RecipeCategoriesRepository;
import com.boldyrev.foodhelper.services.RecipeCategoriesService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeCategoriesServiceImpl implements RecipeCategoriesService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RecipeCategoriesRepository categoriesRepository;

    @Autowired
    public RecipeCategoriesServiceImpl(RecipeCategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeCategory findById(int id) {
        log.debug("Getting recipe category with id={}", id);
        return categoriesRepository.findById(id).orElseThrow(
            () -> new RecipeCategoryNotFoundException(
                String.format("Recipe category with id=%d not found.", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeCategory findByName(String name) {
        log.debug("Getting recipe category with name={}", name);
        return categoriesRepository.findByNameIgnoreCase(name).orElseThrow(
            () -> new RecipeCategoryNotFoundException(
                String.format("Recipe category with name=%s not found.", name)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeCategory> findAll() {
        log.debug("Getting all recipe categories");
        return categoriesRepository.findAll();
    }

    @Override
    @Transactional
    public RecipeCategory save(RecipeCategory category) {
        log.debug("Saving recipe category with name={}", category.getName());
        return categoriesRepository.save(category);
    }

    @Override
    @Transactional
    public void update(int id, RecipeCategory category) {
        RecipeCategory storedCategory = categoriesRepository.findById(id).orElseThrow(
            () -> new RecipeCategoryNotFoundException(
                String.format("Recipe category with id=%d not found.", id)));

        log.debug("Updating recipe category with id={} and name={}", storedCategory.getId(),
            storedCategory.getName());

        storedCategory.setName(category.getName());
        storedCategory.setRecipes(category.getRecipes());
        storedCategory.setParentCategory(category.getParentCategory());
        storedCategory.setChildCategory(category.getChildCategory());
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Deleting recipe category with id={}", id);
        categoriesRepository.deleteById(id);
    }
}