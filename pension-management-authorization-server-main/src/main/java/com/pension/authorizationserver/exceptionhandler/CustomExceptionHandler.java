package com.pension.authorizationserver.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pension.authorizationserver.exception.UserAlreadyExistsException;



@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<String> UserAlreadyExistsHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
    }
}
