package com.boldyrev.foodhelper.models;

import jakarta.persistence.CascadeType;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_ingredients")
@Data
@NoArgsConstructor
public class Ingredient {

    //todo custom validators for entities

    @Id
    @Column(name = "ingredient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ingredient_name")
    @NotNull(message = "Ingredient name can't be null")
    @Size(message = "Ingredient name length must be between 1 and 100 chars", min = 1, max = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_category_id", referencedColumnName = "ingredient_category_id")
    @NotNull(message = "Ingredient should have category")
    private IngredientCategory category;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recipe> recipes;
}
