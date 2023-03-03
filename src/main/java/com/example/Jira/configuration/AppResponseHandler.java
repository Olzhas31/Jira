package com.example.Jira.configuration;

import com.example.Jira.exception.EmailAlreadyExistsException;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppResponseHandler {

    @ExceptionHandler(value = {EmailAlreadyExistsException.class,
            EntityNotFoundException.class,
            UsernameAlreadyExistsException.class,
            RuntimeException.class
    })
    public String handleException(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return "404";
    }
}
