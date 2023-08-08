package com.boldyrev.foodhelper.services;

import com.boldyrev.foodhelper.models.Ingredient;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;

public interface IngredientsService {

    Ingredient findById(int id);

    Page<Ingredient> findAll(int page, int size);

    Ingredient save(Ingredient ingredient);

    Page<Ingredient> searchByName(String name, int page, int size);

    void update(int id, Ingredient ingredient);

    void delete(int id);
}
