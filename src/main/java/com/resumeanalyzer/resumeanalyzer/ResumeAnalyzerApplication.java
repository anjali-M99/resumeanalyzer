package com.resumeanalyzer.resumeanalyzer;

import org.apache.tika.Tika;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ResumeAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeAnalyzerApplication.class, args);
	}

	@Bean
	public Tika tika() {
		return new Tika();
	}

}
