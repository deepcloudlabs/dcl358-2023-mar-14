package com.example.crm.service;

import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.domain.EventDocument;
import com.example.crm.event.EventDTO;
import com.example.crm.repository.EventDocumentRepository;

@Service
@ConditionalOnProperty(name="messaging.system", havingValue = "apache-kafka")
public class KafkaMessagingService {
	private final KafkaTemplate<String,String> kafkaTemplate;
	private final EventDocumentRepository eventDocumentRepository;

	public KafkaMessagingService(KafkaTemplate<String, String> kafkaTemplate,
			EventDocumentRepository eventDocumentRepository) {
		this.kafkaTemplate = kafkaTemplate;
		this.eventDocumentRepository = eventDocumentRepository;
	}

	//@Retry(name = "messagingRetry", fallbackMethod = "sendMessageFallback")
	@EventListener
	public void sendMessage(EventDTO event) throws Throwable {
		kafkaTemplate.send(event.getTopic(),event.getMessage())
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
