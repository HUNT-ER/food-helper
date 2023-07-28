package com.boldyrev.foodhelper.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class CustomResponse {

    private Object body;

    private String message;

    @JsonProperty("http_status")
    private HttpStatus httpStatus;
}
