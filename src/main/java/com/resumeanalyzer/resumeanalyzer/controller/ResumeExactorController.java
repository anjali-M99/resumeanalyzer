package com.resumeanalyzer.resumeanalyzer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resumeanalyzer.resumeanalyzer.dto.UploadedResumeDto;
import com.resumeanalyzer.resumeanalyzer.service.ResumeExtractorServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class ResumeExactorController {
	
	private static Logger Log = LoggerFactory.getLogger(ResumeExactorController.class);
	
	private final ResumeExtractorServiceImpl service;
	
	@PostMapping("/uploadResume")
	public ResponseEntity<String> uploadResume(@ModelAttribute UploadedResumeDto resumeDto) {
		Log.info("Payload {} ",resumeDto);
		return service.saveResumeToDb(resumeDto);
	}

	@GetMapping("/extractFileData")
	public ResponseEntity<String> extractData(@RequestParam String mailId, @RequestParam String jobId){
		Log.info("Extract Keywords & Resume Data");
		return service.extractPlainData(mailId,jobId);
	}	
}
