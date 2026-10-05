package com.resumeanalyzer.resumeanalyzer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resumeanalyzer.resumeanalyzer.dto.AnalysisResponseDto;
import com.resumeanalyzer.resumeanalyzer.service.ResumeAnalysisServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/ai")
public class ResumeAnalysisController {

    private final ResumeAnalysisServiceImpl  service;
	   
    @PostMapping("/analysis-resume")
    public ResponseEntity<String> analysisResume(@RequestBody String resumeData,
    		@RequestParam String mailId, @RequestParam String jobID) {
        return service.getAnalysisForResume(mailId, jobID, resumeData);
    }
    
    @GetMapping("/run-analysis")
    public ResponseEntity<List<AnalysisResponseDto>> checkForAllCandidate(@RequestParam int yrExp, @RequestParam String jId){
    	return service.getAnalysisForResumeForAll(yrExp,jId);
    }
   
}
