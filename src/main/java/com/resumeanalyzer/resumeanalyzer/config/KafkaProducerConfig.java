package com.resumeanalyzer.resumeanalyzer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.resumeanalyzer.resumeanalyzer.dto.KafkaReqDto;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class KafkaProducerConfig {
	
	private CommonProperties common;
	
	// ProducerFactory defines Kafka producer properties and serializers.
	@Bean
	public ProducerFactory<String, KafkaReqDto> producerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092" );
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(props);
	}

	// KafkaTemplate is the key Spring abstraction used to send messages to Kafka
	@Bean
	public KafkaTemplate<String, KafkaReqDto> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

}
