package com.diptiman.handler;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.diptiman.dto.ErrorMessage;
import com.diptiman.exception.ResourceNotFoundException;

@ControllerAdvice
public class TwitterExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request){
		
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setTimeStamp(new Date().getTime());
		errorMessage.setStatus(HttpStatus.NOT_FOUND.value());
		errorMessage.setTitle("Resource Not Found");
		errorMessage.setDetail(rnfe.getMessage());
		errorMessage.setDeveloperMessage(rnfe.getClass().getName());
		return(new ResponseEntity<>(errorMessage, null, HttpStatus.NOT_FOUND));
	}
}
