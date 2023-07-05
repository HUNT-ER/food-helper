package com.boldyrev.foodhelper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "t_ingredients")
@Data
public class Ingredient {

    @Id
    @Column(name = "ingredient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ingredient_name")
    private String name;

    @Column(name = "ingredient_category_id")
    private int ingredientCategoryId;

    @ManyToOne
    @JoinColumn(name = "ingredient_category_id", referencedColumnName = "ingredient_category_id")
    private IngredientCategory category;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private List<Recipe> recipes;
}
