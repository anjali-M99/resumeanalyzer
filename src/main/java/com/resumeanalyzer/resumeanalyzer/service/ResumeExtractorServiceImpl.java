package com.resumeanalyzer.resumeanalyzer.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.resumeanalyzer.resumeanalyzer.dto.UploadedResumeDto;
import com.resumeanalyzer.resumeanalyzer.entity.JobDetails;
import com.resumeanalyzer.resumeanalyzer.entity.ResumeMetadata;
import com.resumeanalyzer.resumeanalyzer.exception.ApplicationException;
import com.resumeanalyzer.resumeanalyzer.exception.NotFoundException;
import com.resumeanalyzer.resumeanalyzer.repo.JobDescription;
import com.resumeanalyzer.resumeanalyzer.repo.ResumeMetadataRepo;
import com.resumeanalyzer.resumeanalyzer.util.CommonUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResumeExtractorServiceImpl {

	private static Logger Log = LoggerFactory.getLogger("ResumeExtractorServiceImpl.class");
	
	private final ResumeMetadataRepo repo;
	
	private final JobDescription jobRepo;
	
	private final Tika tika;
	
	private final KafkaProducerServiceImpl producer;
	
	public ResponseEntity<String> saveResumeToDb(UploadedResumeDto dto) {
		ResumeMetadata rm = new ResumeMetadata();
		BeanUtils.copyProperties(dto, rm);
		try {
			byte[] fileDataArr = dto.getFileData().getBytes();
			rm.setFileData(fileDataArr);
			rm.setYrExp(dto.getYrExp());
			JobDetails jobDetails = jobRepo.findById(dto.getJobId()).orElseThrow(()-> new NotFoundException("Job not found"));
			rm.setJobId(jobDetails);
			repo.save(rm);
			Log.info("record saved succesfully");
			producer.sendMessage(dto);
			return new ResponseEntity<>("Record added", HttpStatus.OK);
		}catch(NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		}catch(Exception e) {
			Log.info("Failed {} ",e );
			throw new ApplicationException("Failed to save record or to send kafka msg", HttpStatus.BAD_REQUEST,e);
		}
	}
	
	public ResponseEntity<String> extractPlainData(String mailId, String jobId) {
		Log.info("Extract Resume data for mailId {} - Job Id {}", mailId, jobId);
		int id = repo.findId(mailId,jobId);
		byte[] fileData= repo.findByIdUploaderMailId(id);
		try {
		if(fileData.length>0) {
			InputStream fs = new ByteArrayInputStream(fileData); 
			String s = CommonUtil.callTextparser(fs,tika.parseToString(fs));
			Log.info("got data");
			
			repo.updateResumeParseData(s, id);
			Log.info("updated");
			
			return new ResponseEntity<>(s, HttpStatus.OK);
		}else {
			Log.info("no data");
			throw new NotFoundException("Not Found");
		}
		}catch(Exception e) {
			throw new ApplicationException("Error during parsing", HttpStatus.INTERNAL_SERVER_ERROR,e);
			
		}
	}
}
