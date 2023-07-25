package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.IngredientCategoryNotFoundException;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.repositories.IngredientCategoriesRepository;
import com.boldyrev.foodhelper.services.IngredientCategoriesService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            () -> new IngredientCategoryNotFoundException(
                String.format("Category with id=%d not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientCategory findByName(String name) {
        log.debug("Getting ingredient category with name={}", name);
        return categoriesRepository.findByNameIgnoreCase(name)
            .orElseThrow(() -> new IngredientCategoryNotFoundException(
                String.format("Category with name=%s not found", name)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientCategory> findAll() {
        log.debug("Getting all ingredient categories");
        return categoriesRepository.findAll();
    }

    @Override
    @Transactional
    public IngredientCategory save(IngredientCategory category) {
        log.debug("Saving ingredient category with name={}", category.getName());
        return categoriesRepository.save(category);
    }

    @Override
    @Transactional
    public void update(int id, IngredientCategory category) {
        IngredientCategory storedCategory = this.findById(id);

        log.debug("Updating ingredient category with id={} and name={}", storedCategory.getId(),
            storedCategory.getName());

        storedCategory.setChildCategories(category.getChildCategories());
        storedCategory.setName(category.getName());
        storedCategory.setParentCategory(category.getParentCategory());
        storedCategory.setIngredients(category.getIngredients());
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Deleting ingredient category with id={}", id);

        categoriesRepository.deleteById(findById(id).getId());
    }
}
