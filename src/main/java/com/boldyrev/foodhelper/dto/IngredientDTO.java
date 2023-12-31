package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.Exist;
import com.boldyrev.foodhelper.dto.transfer.New;
import com.boldyrev.foodhelper.dto.transfer.NewIngredient;
import com.boldyrev.foodhelper.dto.transfer.NewRecipe;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.util.validators.custom_annotations.NotNullAny;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NotNullAny(groups = {})
@JsonInclude(value = Include.NON_NULL)
public class IngredientDTO {

    @Null(groups = {NewIngredient.class})
    @NotNull(groups = {NewRecipe.class})
    @JsonProperty("id")
    private Integer id;

    @NotBlank(groups = {NewIngredient.class})
    @Size(min = 1, max = 100, groups = {NewIngredient.class})
    @JsonProperty("name")
    private String name;

    @Valid
    @NotNull(groups = {NewIngredient.class})
    @JsonProperty("ingredient_category")
    private IngredientCategoryDTO category;
}
