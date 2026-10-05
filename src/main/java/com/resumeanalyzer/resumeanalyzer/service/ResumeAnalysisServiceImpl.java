package com.resumeanalyzer.resumeanalyzer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.resumeanalyzer.resumeanalyzer.dto.AnalysisResponseDto;
import com.resumeanalyzer.resumeanalyzer.entity.JobDetails;
import com.resumeanalyzer.resumeanalyzer.entity.ResumeMetadata;
import com.resumeanalyzer.resumeanalyzer.exception.ApplicationException;
import com.resumeanalyzer.resumeanalyzer.exception.NotFoundException;
import com.resumeanalyzer.resumeanalyzer.repo.JobDescription;
import com.resumeanalyzer.resumeanalyzer.repo.ResumeMetadataRepo;
import com.resumeanalyzer.resumeanalyzer.util.JacksonUtil;

@Service
public class ResumeAnalysisServiceImpl {
	
	private static Logger Log = LoggerFactory.getLogger(ResumeAnalysisServiceImpl.class);

	private final ChatClient chatClient;
	
	private final ResumeMetadataRepo repo;
	
	private final JobDescription jobRepo;
		
	ResumeAnalysisServiceImpl(ChatClient.Builder builder, ResumeMetadataRepo repo, JobDescription jobRepo){
		this.chatClient= builder.build();
		this.repo= repo;
		this.jobRepo=jobRepo;
	}
	
	public ResponseEntity<String> getAnalysisForResume(String mailId, String jobId, String resumeData) {
		Optional<JobDetails> job= jobRepo.findById(jobId);
		Log.info(" {}",job);
		var message = new StringBuilder().append("Act as a Resume Analysiser, analysis the resume for "
				+ "the respective mailId and help recuirter to understand whether the candidate"
				+ " fits for the given job. return the 'matchScore' & 'aiSuggestion' AI Suggestion "
				+ "should be small and better to understand the recruiter which states whether candidate best fit for the role or not."
				+ "matchScore should be int value, yrExp int value"
				+ "This the job description -")
				.append(job)
				.append("This are the user Details -").append(mailId)
				.append(" Now pls read resume data and return the response in the below format ").append(resumeData)
				.append(responseForUser()).append(" Do not give Explanation of Scores and Suggestions only retrun the response as the given json")
				.toString();
		 String rawData= chatClient.prompt(message).call()
				.content();
		 rawData= rawData.trim().replace("```"," ")
			 		.replace("json"," ");
		 Log.info("AI Response {}",rawData);
		 //now update it to DB 
			try {
				AnalysisResponseDto rs = JacksonUtil.convertToDto(rawData, AnalysisResponseDto.class);
			    Log.info("Logger info {} ",rs);
			    var matchScore =rs.getMatchScore();
			    var aiSuggestion=rs.getAiSuggestion();
			    repo.updateMatchScore_Suggestion(matchScore,aiSuggestion,mailId, jobId);
			    Log.info("Record updated");
			    return ResponseEntity.ok("Success");
			}catch(Exception e) {
				throw new ApplicationException("Error During Analysis of Resumse", HttpStatus.INTERNAL_SERVER_ERROR, e);
			}
			
	
	}
	
	public ResponseEntity<List<AnalysisResponseDto>> getAnalysisForResumeForAll(int yearExp, String jobId) {
		Log.info("Inside getAnalysisForResumeForAll ");
		LocalDateTime lastUpdatedtime = LocalDateTime.now().minusDays(7);
		Log.info("lastUpdatedtime {}",lastUpdatedtime);
		Log.info("yearExp {}",yearExp);
		Log.info("JobId {}",jobId);
		//JobDetails jobId = jobRepo.findById(jId).orElseThrow(()-> new RuntimeException("Job not found"));
		
		List<ResumeMetadata> listRM = repo.fetchRecordForJobMatch(yearExp,jobId,lastUpdatedtime);
		Log.info("fetched data {}",listRM.size());
		List<AnalysisResponseDto> listUpdated = updateAnalysisResponse(listRM);
		if(!listUpdated.isEmpty())
			return ResponseEntity.ok(listUpdated);
		else
			Log.info("List data is empty");
			throw new NotFoundException("No record exists");
		
	}
	

	public List<AnalysisResponseDto> updateAnalysisResponse(List<ResumeMetadata> listRM){
		List<AnalysisResponseDto> listAnalysisRes= new ArrayList();
		for(ResumeMetadata rm: listRM) {
			AnalysisResponseDto anaRes = new AnalysisResponseDto();
			anaRes.setId(rm.getId());
			anaRes.setName(rm.getUploaderName());
			anaRes.setMailId(rm.getUploaderMailId());
			anaRes.setYrExp(rm.getYrExp());
			anaRes.setMatchScore(rm.getMatchScore());
			anaRes.setAiSuggestion(rm.getAiSuggestion());
			listAnalysisRes.add(anaRes);
		}
		Log.info("List of Analysis {} :",listAnalysisRes);
		return listAnalysisRes;
	}
	
	public String responseFormat() {
		return """
				[
				    {
				      "id":"",
				      "name" :"",
					  "mailId": "",
					  "yrExp": "",
					  "matchScore" :"",
					  "aiSuggestion": ""
				    }
				]
			 """ ;
	}
	
	public String responseForUser() {
		return """
				   {
				      "name" :"",
					  "mailId": "",
					  "yrExp": "",
					  "matchScore" :"",
					  "aiSuggestion": ""
				    }

				""";
	}
}
