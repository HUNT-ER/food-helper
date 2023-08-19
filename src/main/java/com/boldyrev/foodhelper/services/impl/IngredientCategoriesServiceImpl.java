package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.repositories.IngredientCategoriesRepository;
import com.boldyrev.foodhelper.services.IngredientCategoriesService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientCategoriesServiceImpl implements IngredientCategoriesService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final IngredientCategoriesRepository categoriesRepository;

    @Autowired
    public IngredientCategoriesServiceImpl(IngredientCategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientCategory findById(int id) {
        log.debug("Getting ingredient category with id={}", id);
        return categoriesRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Category with id=%d not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientCategory> findAll() {

        List<IngredientCategory> categories = categoriesRepository.findAll();

        if (categories.isEmpty()) {
            throw new EmptyDataException("Ingredient categories not found");
        }

        return categories;
    }

    @Override
    @Transactional
    public IngredientCategory save(IngredientCategory category) {
        return categoriesRepository.save(category);
    }

    @Override
    @Transactional
    public void update(int id, IngredientCategory category) {
        IngredientCategory storedCategory = this.findById(id);

        storedCategory.setName(category.getName());
        storedCategory.setParentCategory(category.getParentCategory());
    }

    @Override
    @Transactional
    public void delete(int id) {
        categoriesRepository.deleteById(findById(id).getId());
    }
}
