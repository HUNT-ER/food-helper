package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.NewRecipe;
import com.boldyrev.foodhelper.models.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeIngredientDTO {

    @JsonIgnore
    private Recipe recipe;

    @Valid
    @NotNull(groups = {NewRecipe.class})
    @JsonProperty("ingredient")
    private IngredientDTO ingredient;
}
