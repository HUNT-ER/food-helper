package com.boldyrev.foodhelper.models;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.ToString;

@Entity
@Table(name = "t_recipes_ingredients")
@AssociationOverrides({
    @AssociationOverride(name = "id.recipe", joinColumns = @JoinColumn(name = "recipe_id")),
    @AssociationOverride(name = "id.ingredient", joinColumns = @JoinColumn(name = "ingredient_id"))
})
public class RecipeIngredient {
    @EmbeddedId
    private RecipeIngredientId id = new RecipeIngredientId();

    public Recipe getRecipe() {
        return id.getRecipe();
    }

    public Ingredient getIngredient() {
        return id.getIngredient();
    }

    public void setRecipe(Recipe recipe) {
        id.setRecipe(recipe);
    }

    public void setIngredient(Ingredient ingredient) {
        id.setIngredient(ingredient);
    }

    public RecipeIngredientId getId() {
        return id;
    }

    public void setId(RecipeIngredientId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeIngredient)) {
            return false;
        }
        RecipeIngredient that = (RecipeIngredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
