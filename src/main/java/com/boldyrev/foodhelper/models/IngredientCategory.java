package com.boldyrev.foodhelper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "t_ingredients_categories")
@Data
public class IngredientCategory {

    @Id
    @Column(name = "ingredient_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ingredient_category_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id",referencedColumnName = "ingredient_category_id")
    private IngredientCategory parentCategory;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "ingredient_category_id")
    private List<IngredientCategory> childCategories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Ingredient> ingredients;



















//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_category_id", referencedColumnName = "ingredient_category_id")
//    private IngredientCategory parentCategory;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_category_id")
//    private List<IngredientCategory> childCategories;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
//    private List<Ingredient> ingredients;
}
