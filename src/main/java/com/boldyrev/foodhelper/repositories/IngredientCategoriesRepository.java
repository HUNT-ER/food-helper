package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.IngredientCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientCategoriesRepository extends JpaRepository<IngredientCategory, Integer> {

    Optional<IngredientCategory> findByNameIgnoreCase(String name);

    @Query("select c,p from IngredientCategory c left join c.parentCategory p")
    List<IngredientCategory> findAll();

    @Query("select c,p from IngredientCategory c left join c.parentCategory p where c.id = :id")
    Optional<IngredientCategory> findById(@Param("id") Integer id);

}
