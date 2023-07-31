package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.RecipeCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeCategoriesRepository extends JpaRepository<RecipeCategory, Integer> {

    Optional<RecipeCategory> findByNameIgnoreCase(String name);

    @Query("select r,p from RecipeCategory r left join r.parentCategory p")
    List<RecipeCategory> findAll();

    @Query("select r,p from RecipeCategory r left join r.parentCategory p where r.id = :id")
    Optional<RecipeCategory> findById(@Param("id") Integer id);
}
