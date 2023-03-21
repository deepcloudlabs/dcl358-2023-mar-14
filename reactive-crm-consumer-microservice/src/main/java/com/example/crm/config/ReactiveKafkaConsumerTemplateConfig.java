package com.example.crm.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class ReactiveKafkaConsumerTemplateConfig {

	@Bean
	ReceiverOptions<String,String> createReceiverOptions(
			@Value("${topicName}") String topicName, KafkaProperties props) {
		ReceiverOptions<String,String> receiverOptions = ReceiverOptions.create(props.buildConsumerProperties());
		return receiverOptions.subscription(Collections.singletonList(topicName));
	}
	
	@Bean
	ReactiveKafkaConsumerTemplate<String,String> createTemplate(ReceiverOptions<String,String> opts){
		return new ReactiveKafkaConsumerTemplate<>(opts);
	}
}
