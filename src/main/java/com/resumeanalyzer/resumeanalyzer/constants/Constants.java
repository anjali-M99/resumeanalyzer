package com.resumeanalyzer.resumeanalyzer.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

	public static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 mins
	
	public static final String USER_URI = "/user/**";
	
	public static final String LOGIN_URI ="/auth/**";
	
	public static final String ADMIN_URI = "/ai/run-analysis";
	
	public static final String ADMIN_ANALYSIS_URI="/ai/analysis-resume";
	
	public static final String BEARER = "Bearer";
	
	public static final String AUTHORIZATION ="Authorization";
	
	public static final String TOPIC_NAME = "resume_analysis_topics";
	
	public static final String IMG_REGEX ="image\\d+\\.(png|jpg|jpeg|gif)";
	
	public static final String HTML_REGEX = "<[^>]*>";
	
	public static final String NUMRIC_REGEX = "[^a-zA-Z0-9\\s]";
	
	public static final String EMPTY_STRING =" ";
	
	
	
	   

}
