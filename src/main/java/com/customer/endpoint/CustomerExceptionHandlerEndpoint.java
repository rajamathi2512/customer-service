package com.customer.endpoint;

import com.customer.exception.CustomerNotFoundException;
import com.customer.model.ApiErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomerExceptionHandlerEndpoint {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(CustomerNotFoundException ex, WebRequest request)
    {
        return new ResponseEntity<>(ApiErrorResponse.builder()
                                                    .message(ex.getMessage())
                                                    .timestamp(LocalDateTime.now())
                                                    .statusCode(NOT_FOUND.value())
                                                    .description(request.getDescription(false))
                                                    .build(), NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                            WebRequest request)
    {

            return new ResponseEntity<>(ApiErrorResponse.builder()
                                                    .timestamp(LocalDateTime.now())
                                                    .statusCode(BAD_REQUEST.value())
                                                    .description(request.getDescription(false))
                                                    .errors(ex.getBindingResult()
                                                              .getFieldErrors()
                                                              .stream()
                                                              .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                              .collect(Collectors.toList()))
                                                    .build(), BAD_REQUEST);

    }
}
    