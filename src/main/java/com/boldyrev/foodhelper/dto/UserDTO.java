package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.Exist;
import com.boldyrev.foodhelper.dto.transfer.New;
import com.boldyrev.foodhelper.dto.transfer.NewRecipe;
import com.boldyrev.foodhelper.dto.transfer.NewUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserDTO {

    @Null(groups = {NewUser.class})
    @NotNull(groups = {NewRecipe.class})
    @JsonProperty("id")
    private Integer id;

    @NotBlank(groups = {NewUser.class})
    @Pattern(message = "Username should have only a-zA-Z0-9_ symbols", regexp = "\\w+",
        groups = {NewUser.class})
    @Size(min = 5, max = 25, groups = {NewUser.class})
    @JsonProperty("username")
    private String username;
}
