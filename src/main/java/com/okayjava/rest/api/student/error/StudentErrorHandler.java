package com.okayjava.rest.api.student.error;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentErrorHandler {

	@ExceptionHandler(StudentException.class)
	public ResponseEntity<ErrorType> handleNotFound(StudentException snf){
		
		return new ResponseEntity<ErrorType>(
				new ErrorType(
						new Date(System.currentTimeMillis()).toString(), 
						snf.code.toString(), 
						snf.error), 
				snf.code);
		
		
	}
}
