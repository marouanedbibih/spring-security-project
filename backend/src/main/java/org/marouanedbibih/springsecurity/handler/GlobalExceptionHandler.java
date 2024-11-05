package org.marouanedbibih.springsecurity.handler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.marouanedbibih.springsecurity.exception.AlreadyExistException;
import org.marouanedbibih.springsecurity.exception.MyAuthException;
import org.marouanedbibih.springsecurity.exception.MyNotDeleteException;
import org.marouanedbibih.springsecurity.exception.MyNotFoundException;
import org.marouanedbibih.springsecurity.exception.MyNotSave;
import org.marouanedbibih.springsecurity.handler.errors.MyErrRes;
import org.marouanedbibih.springsecurity.handler.errors.MyErrorField;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle AlreadyExistException
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<MyErrRes> handleAlreadyExistException(AlreadyExistException ex) {
        return new ResponseEntity<>(ex.getResponse(), HttpStatus.CONFLICT);
    }

    // Handle NotFoundException
    @ExceptionHandler(MyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MyErrRes> handleNotFoundException(MyNotFoundException ex) {
        return new ResponseEntity<>(ex.getResponse(), HttpStatus.NOT_FOUND);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MyErrRes> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<MyErrorField> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());

        MyErrRes errorResponse = MyErrRes.builder()
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Helper method to map field errors
    private MyErrorField mapFieldError(FieldError fieldError) {
        return MyErrorField.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }

    // Handle MyAuthException
    @ExceptionHandler(MyAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MyErrRes> handleMyAuthException(MyAuthException ex) {
        return new ResponseEntity<>(ex.getResponse(), HttpStatus.UNAUTHORIZED);
    }

    // Handle MyNotSave
    @ExceptionHandler(MyNotSave.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MyErrRes> handleMyNotSave(MyNotSave ex) {
        return new ResponseEntity<>(ex.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle MyNotDeleteException
    @ExceptionHandler(MyNotDeleteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MyErrRes> handleMyNotDeleteException(MyNotDeleteException ex) {
        return new ResponseEntity<>(ex.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle IOException
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MyErrRes> handleIOException(IOException ex) {
        return new ResponseEntity<>(MyErrRes.builder()
                .message("An error occurred while processing the request.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}