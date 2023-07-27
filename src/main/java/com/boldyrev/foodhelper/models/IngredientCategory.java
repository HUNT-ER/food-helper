package com.boldyrev.foodhelper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_ingredients_categories")
@Data
@NoArgsConstructor
public class IngredientCategory {

    @Id
    @Column(name = "ingredient_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ingredient_category_name")
    @NotNull(message = "Ingredient category name can't be null")
    @Size(message = "Ingredient category name length must be between 1 and 100 chars", min = 1, max = 100)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "ingredient_category_id")
    private IngredientCategory parentCategory;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "ingredient_category_id")
    private List<IngredientCategory> childCategories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Ingredient> ingredients;

}
