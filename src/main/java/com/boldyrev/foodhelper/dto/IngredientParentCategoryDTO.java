package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.NewCategory;
import com.boldyrev.foodhelper.dto.transfer.NewIngredient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class IngredientParentCategoryDTO {

    @NotNull(groups = {NewCategory.class})
    @JsonProperty("id")
    private Integer id;

    @Size(min = 1, max = 100, groups = {NewCategory.class})
    @JsonProperty("name")
    private String name;
}
