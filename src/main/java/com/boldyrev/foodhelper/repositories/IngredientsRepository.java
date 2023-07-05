package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredient, Integer> {

}
