package com.pension.pensionerdetail.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pension.pensionerdetail.exception.AlreadyExistsException;
import com.pension.pensionerdetail.exception.UserNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> userNotFound(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Details found for this Aadhar Number");
    }
	
	@ExceptionHandler(AlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<String> alreadyExists(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Details are already available for this Aadhar Number");
    }
	
}

