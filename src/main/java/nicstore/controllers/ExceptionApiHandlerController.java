package nicstore.controllers;

import nicstore.dto.auth.ValidationErrorResponse;
import nicstore.dto.auth.Violation;
import nicstore.dto.product.ResponseException;
import nicstore.exceptions.*;
import nicstore.exceptions.auth.UserAlreadyExistException;
import nicstore.exceptions.auth.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionApiHandlerController {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage(),
                                ZonedDateTime.now(ZoneId.of("Z"))
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(violation -> new Violation(
                                violation.getField(),
                                violation.getDefaultMessage(),
                                ZonedDateTime.now(ZoneId.of("Z"))
                        )
                ).collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ExceptionHandler(value = {
            UserAlreadyExistException.class,
            UserNotFoundException.class,
            ProductAlreadyInCartException.class,
            ProductOutInStockException.class,
            ProductNotFoundException.class,
            BadOperationException.class,
            NotEnoughProductsInStockException.class,
            ImageUploadException.class,
            ReviewNotExistingException.class,
            CartNotFoundException.class,
            EmptyCartException.class
    })
    public ResponseException onMethodArgumentNotValidException(RuntimeException e) {
        return ResponseException.builder()
                .message(e.getMessage())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
    }
}
