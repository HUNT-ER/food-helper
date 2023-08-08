package com.boldyrev.foodhelper.services;

import com.boldyrev.foodhelper.models.IngredientCategory;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface IngredientCategoriesService {

    IngredientCategory findById(int id);

    List<IngredientCategory> findAll();

    IngredientCategory save(IngredientCategory category);

    void update(int id, IngredientCategory category);

    void delete(int id);
}
