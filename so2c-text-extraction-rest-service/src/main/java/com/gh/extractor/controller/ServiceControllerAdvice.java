package com.gh.extractor.controller;

import com.gh.extractor.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ServiceControllerAdvice {

    /*
        Triggered when client submits invalid input to service
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        String validationErrorMessages = "";

        int errorCount = 1;
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String errorMessage = objectError.getDefaultMessage();
            validationErrorMessages += String.format("%1s: %2s. ", errorCount++, errorMessage);
        }
        return ErrorResponse
                .builder()
                .date(Instant.now())
                .message(validationErrorMessages)
                .build();
    }
}
