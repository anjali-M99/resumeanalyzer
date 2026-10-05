package com.resumeanalyzer.resumeanalyzer.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponseDto {
	
	private HttpStatus errorCode;
	
	private String errorMsg;
	
	private LocalDateTime dateTime;

}
