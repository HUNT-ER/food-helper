package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.Ingredient;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientsRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findByNameIgnoreCase(String name);

    @Query("select c from Ingredient c left join fetch c.category r where upper(c.name) like concat('%', upper(:name), '%')")
    Page<Ingredient> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("select c from Ingredient c left join fetch c.category r")
    Page<Ingredient> findAll(Pageable pageable);

    @Query("select c from Ingredient c join fetch c.category r where c.id = :id")
    Optional<Ingredient> findById(@Param("id") Integer id);

    @Query("select i from Ingredient i where i.id in :ingredients")
    List<Ingredient> findAllById(@Param("ingredients") Collection<Ingredient> ingredients);

    @Query("select i from Ingredient i join fetch i.category where i.category.id = :id")
    Page<Ingredient> findAllByCategoryId(@Param("id") int id, Pageable pageable);
}
