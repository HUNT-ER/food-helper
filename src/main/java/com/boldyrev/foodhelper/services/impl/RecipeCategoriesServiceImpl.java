package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
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
        return categoriesRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Recipe category with id=%d not found.", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeCategory> findAll() {
        List<RecipeCategory> recipeCategories = categoriesRepository.findAll();

        if (recipeCategories.isEmpty()) {
            throw new EmptyDataException("Recipe categories not found");
        }

        return recipeCategories;
    }

    @Override
    @Transactional
    public RecipeCategory save(RecipeCategory category) {
        return categoriesRepository.save(category);
    }

    @Override
    @Transactional
    public void update(int id, RecipeCategory category) {
        RecipeCategory storedCategory = findById(id);
        storedCategory.setName(category.getName());
        storedCategory.setParentCategory(category.getParentCategory());
    }

    @Override
    @Transactional
    public void delete(int id) {
        categoriesRepository.deleteById(id);
    }
}
