package com.example.crm.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.crm.repository.EventDocumentRepository;

@Service
public class OutboxRetryService {
	private final EventDocumentRepository documentRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;

	public OutboxRetryService(EventDocumentRepository documentRepository, KafkaTemplate<String, String> kafkaTemplate) {
		this.documentRepository = documentRepository;
		this.kafkaTemplate = kafkaTemplate;
	}

	@Scheduled(fixedRate = 60_000)
	public void retryFailedMessages() {
		documentRepository.findAll().forEach(event -> {
			kafkaTemplate.send(event.getTopicName(),event.getEvent())
			             .addCallback(result -> {
			            	 documentRepository.delete(event);
			             },System.err::println);
		});
	}
}
