package com.boldyrev.foodhelper.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import jdk.jfr.Description;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_recipes")
@Data
@NoArgsConstructor
public class Recipe {

    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotNull(message = "Title can't be null")
    @Size(message = "Title length should be between 1 and 150 chars", min = 1, max = 150)
    private String title;

    @Column(name = "description")
    @NotNull(message = "Description can't be null")
    @NotEmpty(message = "Description can't be empty")
    private String description;

    @Column(name = "image_path")
    @NotNull(message = "Image path can't be null")
    private String imagePath;

    @Transient
    @NotNull(message = "Image download link can't be null")
    private String imageLink;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_category_id")
    @NotNull(message = "Recipe category can't be null")
    private RecipeCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "Recipe creator can't be null")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "t_recipes_ingredients",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;
}
