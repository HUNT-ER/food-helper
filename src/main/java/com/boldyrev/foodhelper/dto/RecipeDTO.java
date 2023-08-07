package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.New;
import com.boldyrev.foodhelper.dto.transfer.NewRecipe;
import com.boldyrev.foodhelper.util.validators.custom_annotations.NotNullAny;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@NotNullAny
public class RecipeDTO {

    @Null(groups = {NewRecipe.class})
    @JsonProperty("id")
    private Integer id;

    @NotBlank(groups = {NewRecipe.class})
    @Size(min = 1, max = 150, groups = {NewRecipe.class})
    @JsonProperty("title")
    private String title;

    @NotBlank(groups = {NewRecipe.class})
    @JsonProperty("description")
    private String description;

    @Valid
    @NotNull(groups = {NewRecipe.class})
    @JsonProperty("category")
    private RecipeCategoryDTO category;

    @Valid
    @NotNull(groups = {New.class})
    @JsonProperty("creator")
    private UserDTO creator;

    @Valid
    @NotNull(groups = {New.class})
    @JsonProperty("ingredients")
    private Set<RecipeIngredientDTO> recipeIngredients;
}
