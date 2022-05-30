package com.pension.processpensioninput.exceptionhandler;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pension.processpensioninput.exception.DetailsNotFoundException;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DetailsNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> userNotFound(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Details found for this Aadhar Number");
    }
	
	@ExceptionHandler(FeignException.class)
	@ResponseBody
    public ResponseEntity<String> userNotFound(FeignException e, HttpServletResponse response) {
		log.info("Proxy throwing error ....");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Details found for this Aadhar Number");
    }
}