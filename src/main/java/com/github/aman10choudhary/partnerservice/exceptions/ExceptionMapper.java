package com.github.aman10choudhary.partnerservice.exceptions;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import java.lang.reflect.Method;
import java.util.*;

import static com.github.aman10choudhary.partnerservice.utilities.ApplicationConstants.Errors.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        return createResponseEntity(constraintViolations);
    }

    public ResponseEntity createResponseEntity(Set<ConstraintViolation<?>> constraintViolations){
        ErrorResponse error = new ErrorResponse();
        for(ConstraintViolation constraintViolation : constraintViolations){
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage(constraintViolation.getMessage().trim());
        }
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception){
        ErrorResponse error = new ErrorResponse();
        BindingResult bindingResult = exception.getBindingResult();
        for(ObjectError objectError : bindingResult.getAllErrors()){
                error.setCode(HttpStatus.BAD_REQUEST.value());
                error.setMessage(objectError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception exception){
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(error.getCode()).body(error);
}

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleException(BadRequestException exception){
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({PartnerNotFoundException.class})
    public ResponseEntity<?> handleException(PartnerNotFoundException exception){
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotReadableException exception){
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(getMessage(exception));
        return ResponseEntity.badRequest().body(error);
    }

    private String getMessage(HttpMessageNotReadableException exception) {
        return exception.getCause() != null ?
                (exception.getCause().getCause() != null ? exception.getCause().getCause().getMessage() : exception.getCause().getMessage()) :
                exception.getMessage();
    }
}
