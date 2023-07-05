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
@Table(name = "t_recipes_categories")
@Data
public class RecipeCategory {

    @Id
    @Column(name = "recipe_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "recipe_category_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "recipe_category_id")
    private RecipeCategory parentCategory;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "recipe_category_id")
    private List<RecipeCategory> childCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Recipe> recipes;
}
