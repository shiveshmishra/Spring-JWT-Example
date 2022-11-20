package com.okayjava.rest.api.student.error;

import org.springframework.http.HttpStatus;

public class StudentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	String error;
	HttpStatus code;
	
	public StudentException() {
		super();
	}

	public StudentException(String error, HttpStatus code) {
		this.error = error;
		this.code = code;
	}

	
	
}
