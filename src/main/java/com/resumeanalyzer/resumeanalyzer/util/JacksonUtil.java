package com.resumeanalyzer.resumeanalyzer.util;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resumeanalyzer.resumeanalyzer.dto.AnalysisResponseDto;
import com.resumeanalyzer.resumeanalyzer.exception.JacksonException;

public class JacksonUtil {

	
	private static final ObjectMapper obj = new ObjectMapper();
	
	public static JsonNode parse(String str) {
		try {
			return obj.readTree(str);
		} catch (Exception e) {
			throw new JacksonException("Invalid JSON string");
		}
	}

	public static List<AnalysisResponseDto> convertToList(String s) {
		try {
			ObjectMapper obj = new ObjectMapper();
			return obj.readValue(s, new TypeReference<List<AnalysisResponseDto>>() {
			});
		} catch (Exception e) {
			throw new JacksonException("Failed to parse JSON");
		}
	}
	
	public static <T>T convertToDto(String s, Class<T> clazz) {
		try {
			ObjectMapper obj = new ObjectMapper();
			return obj.readValue(s, clazz) ;
		} catch (Exception e) {
			throw new JacksonException("Failed to parse JSON");
		}
	}
}
