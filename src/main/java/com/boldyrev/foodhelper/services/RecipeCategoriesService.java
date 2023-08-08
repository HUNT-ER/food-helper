package com.boldyrev.foodhelper.services;

import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.models.RecipeCategory;
import java.util.List;

public interface RecipeCategoriesService {

    RecipeCategory findById(int id);

    List<RecipeCategory> findAll();

    RecipeCategory save(RecipeCategory category);

    void update(int id, RecipeCategory category);

    void delete(int id);
}
