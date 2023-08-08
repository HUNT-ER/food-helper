package com.boldyrev.foodhelper.exception_handling;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityAlreadyExistsException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
import com.boldyrev.foodhelper.exceptions.ValidationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleException(ValidationException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({EntityNotFoundException.class, EmptyDataException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityAlreadyExistsException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException e) {
        //todo пересмотреть кастомное сообщение
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
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
