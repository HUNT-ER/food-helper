package com.boldyrev.foodhelper.services;

import com.boldyrev.foodhelper.models.Ingredient;
import java.util.List;

public interface IngredientsService {

    Ingredient findById(int id);

    Ingredient findByName(String name);

    List<Ingredient> findAll();

    Ingredient save(Ingredient ingredient);

    void update(int id, Ingredient ingredient);

    void delete(int id);
}
