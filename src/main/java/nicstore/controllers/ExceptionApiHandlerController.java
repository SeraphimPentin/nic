package nicstore.controllers;

import nicstore.exceptions.auth.RegisterValidationException;
import nicstore.exceptions.auth.UserAlreadyExistException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandlerController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RegisterValidationException.class, UserAlreadyExistException.class})
    public ErrorMessage handleException(Exception exception){
        return new ErrorMessage(exception.getMessage(), exception.getCause());
    }
}
