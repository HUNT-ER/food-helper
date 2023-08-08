package com.boldyrev.foodhelper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeImageDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("image_link")
    private String imageLink;
}
