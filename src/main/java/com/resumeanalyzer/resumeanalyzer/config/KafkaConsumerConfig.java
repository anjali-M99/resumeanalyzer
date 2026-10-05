package com.resumeanalyzer.resumeanalyzer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.resumeanalyzer.resumeanalyzer.dto.KafkaReqDto;

import lombok.AllArgsConstructor;

@EnableKafka
@Configuration
@AllArgsConstructor
public class KafkaConsumerConfig {
	
	private CommonProperties common;
	
	// ConsumerFactory sets up deserializers and connection settings.
	@Bean
    public ConsumerFactory<String, KafkaReqDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, common.getGrpId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaReqDto.class)
            );
    }

    // ConcurrentKafkaListenerContainerFactory manages Kafka listener threads.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaReqDto>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaReqDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}