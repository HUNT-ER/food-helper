package com.boldyrev.foodhelper.services;

import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.models.Recipe;
import java.util.List;

public interface RecipesService {

    Recipe findById(int id);

    Recipe findByTitle(String title);

    List<Recipe> findAll();

    List<Recipe> findAllByIngredients(List<Ingredient> ingredients);

    Recipe save(Recipe recipe);

    void update(int id, Recipe recipe);

    void delete(int id);
}
