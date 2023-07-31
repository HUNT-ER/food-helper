package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.Ingredient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientsRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findByNameIgnoreCase(String name);

    @Query("select c,r from Ingredient c left join c.category r where upper(c.name) like concat('%', upper(:name), '%')")
    List<Ingredient> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("select c,r,p from Ingredient c left join c.category r left join r.parentCategory p order by c.name")
    List<Ingredient> findAll();

    @Query("select c,r from Ingredient c left join c.category r where c.id = :id")
    Optional<Ingredient> findById(@Param("id") Integer id);
}
