package com.resumeanalyzer.resumeanalyzer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class AnalysisResponseDto {

	private int id;
	
	private String name;
	
	private String mailId;
	
	private int yrExp;
	
	private int matchScore;
	
	private String aiSuggestion;
}
