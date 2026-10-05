package com.resumeanalyzer.resumeanalyzer.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import com.resumeanalyzer.resumeanalyzer.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {
	
	@ExceptionHandler(ApplicationException.class)
	public  ResponseEntity<ErrorResponseDto> applicationException(ApplicationException ex){
		var err =new ErrorResponseDto(ex.getHttpStatus(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(err,ex.getHttpStatus());
	}

	@ExceptionHandler(JacksonException.class)
	public  ResponseEntity<ErrorResponseDto> jacksonException(JacksonException ex){
		 var err =new ErrorResponseDto(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
		 return new ResponseEntity<ErrorResponseDto>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponseDto> notFoundException(NotFoundException ex ){
	     ErrorResponseDto error  = new ErrorResponseDto(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
