package com.boldyrev.foodhelper.exception_handling;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
import com.boldyrev.foodhelper.exceptions.ImageNotSavedException;
import com.boldyrev.foodhelper.exceptions.ValidationException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({EntityNotFoundException.class, EmptyDataException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        log.debug("Trying to get entities with bad parameters: {}", e.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({ConstraintViolationException.class, ValidationException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception e) {
        log.debug("Incorrect validation. Message: {}.", e.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    //todo пересмотреть для ошибок типа не существования ключа
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException e) {
//        return ResponseEntity.badRequest()
//            .body(new ErrorResponse("This object already exists", HttpStatus.BAD_REQUEST));
//    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<ErrorResponse> handeException(AmazonS3Exception e) {
        log.error("Data not saved in S3 with message '{}' and cause: {}", e.getMessage(),
            e.getCause());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ImageNotSavedException.class)
    public ResponseEntity<ErrorResponse> handleException(ImageNotSavedException e) {
        log.error("Recipe image not saved cause: {}", e.getCause());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
