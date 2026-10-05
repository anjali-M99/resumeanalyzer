package com.resumeanalyzer.resumeanalyzer.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final HttpStatus httpStatus;
		
	public ApplicationException(String msg, HttpStatus status) {
		super(msg);
		this.httpStatus= status;
	}
	
	
	public ApplicationException(String msg, HttpStatus status, Exception e) {
		super(msg,e);
		this.httpStatus= status;
	}

}
