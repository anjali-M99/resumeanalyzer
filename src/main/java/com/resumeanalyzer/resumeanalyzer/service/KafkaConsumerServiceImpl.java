package com.resumeanalyzer.resumeanalyzer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.resumeanalyzer.resumeanalyzer.constants.Constants;
import com.resumeanalyzer.resumeanalyzer.dto.KafkaReqDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KafkaConsumerServiceImpl {
	
	private static Logger Log = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);
	
	private final ResumeExtractorServiceImpl service;
	
	private final ResumeAnalysisServiceImpl analysisService;
	
	@KafkaListener(topics = Constants.TOPIC_NAME, groupId = "resume-consumer")
	public ResponseEntity<String> consume(KafkaReqDto dto) {
		Log.info("Listerner recived {}: " + dto.getMailId());
		ResponseEntity<String> res =service.extractPlainData(dto.getMailId(),dto.getJobId());
		Log.info("**File parsed success***");
		return analysisService.getAnalysisForResume(dto.getMailId(), dto.getJobId(), res.getBody());
	}

}
