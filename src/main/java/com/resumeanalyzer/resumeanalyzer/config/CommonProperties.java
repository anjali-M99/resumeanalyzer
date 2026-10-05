package com.resumeanalyzer.resumeanalyzer.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class CommonProperties {

	@Value("$spring.kafka.consumer.group-id")
	private String grpId;
	
	@Value("$spring.kafka.consumer.auto-offset-reset")
	private String offsetReset;
	
	@Value("$spring.kafka.consumer.key-deserializer")
	private String deserializerKey;
	
	@Value("$spring.kafka.consumer.value-deserialize")
	private String deserializerValue;
	
	@Value("$spring.kafka.producer.key-serializer")
	private String serializerKey;
	
	@Value("spring.kafka.producer.value-serializer")
	private String serializerValue;
	
	@Value("$kafka.topic")
	private String kafkaTopicNm;
	
}
