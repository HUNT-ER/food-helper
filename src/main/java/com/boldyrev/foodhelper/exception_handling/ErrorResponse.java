package com.boldyrev.foodhelper.exception_handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;

    private HttpStatus httpStatus;
}
