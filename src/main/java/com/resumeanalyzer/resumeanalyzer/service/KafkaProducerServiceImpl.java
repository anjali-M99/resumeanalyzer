package com.resumeanalyzer.resumeanalyzer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.resumeanalyzer.resumeanalyzer.constants.Constants;
import com.resumeanalyzer.resumeanalyzer.dto.KafkaReqDto;
import com.resumeanalyzer.resumeanalyzer.dto.UploadedResumeDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KafkaProducerServiceImpl {
	
	private final KafkaTemplate<String, KafkaReqDto> kafkaTemplate;

	public void sendMessage(UploadedResumeDto message) {
		KafkaReqDto req = new KafkaReqDto();
		req.setJobId(message.getJobId());
		req.setMailId(message.getUploaderMailId());
		
		kafkaTemplate.send(Constants.TOPIC_NAME, req);
		System.out.println("Message sent for mailId {}: " + message);
	}

}
