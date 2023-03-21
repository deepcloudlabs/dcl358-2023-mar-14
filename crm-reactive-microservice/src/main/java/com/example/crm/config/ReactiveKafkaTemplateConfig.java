package com.example.crm.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import reactor.kafka.sender.SenderOptions;

@Configuration
public class ReactiveKafkaTemplateConfig {

	@Bean
	ReactiveKafkaProducerTemplate<String, String> createReactiveKafkaProducerTemplate(KafkaProperties props){
		var properties = props.buildProducerProperties();
		return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(properties));
	}
}
