package com.example.crm.service;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.domain.EventDocument;
import com.example.crm.repository.EventDocumentRepository;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class MessagingService {
	private final KafkaTemplate<String,String> kafkaTemplate;
	private final EventDocumentRepository eventDocumentRepository;

	public MessagingService(KafkaTemplate<String, String> kafkaTemplate,
			EventDocumentRepository eventDocumentRepository) {
		this.kafkaTemplate = kafkaTemplate;
		this.eventDocumentRepository = eventDocumentRepository;
	}

	@Retry(name = "messagingRetry", fallbackMethod = "sendMessageFallback")
	public void sendMessage(String topic,String message) throws Throwable {
		kafkaTemplate.send(topic,message)
		             .addCallback(System.out::println,System.err::println);
	}

	public void sendMessageFallback(String topicName,String message,Throwable t) {
		// implement outbox pattern
		System.err.println("Running sendMessageFallback(%s,%s)...".formatted(topicName,message)); 
		var eventDocument = new EventDocument();
		eventDocument.setId(UUID.randomUUID().toString());
		eventDocument.setEvent(message);
		eventDocument.setTopicName(topicName);
		eventDocumentRepository.insert(eventDocument);
	}
}
