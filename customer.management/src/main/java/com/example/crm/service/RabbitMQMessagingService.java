package com.example.crm.service;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.crm.domain.EventDocument;
import com.example.crm.event.EventDTO;
import com.example.crm.repository.EventDocumentRepository;

@Service
@ConditionalOnProperty(name="messaging.system", havingValue = "rabbitmq")
public class RabbitMQMessagingService {
	private final RabbitTemplate rabbitTemplate;
	private final EventDocumentRepository eventDocumentRepository;

	public RabbitMQMessagingService(RabbitTemplate rabbitTemplate,
			EventDocumentRepository eventDocumentRepository) {
		this.rabbitTemplate = rabbitTemplate;
		this.eventDocumentRepository = eventDocumentRepository;
	}

	//@Retry(name = "messagingRetry", fallbackMethod = "sendMessageFallback")
	@EventListener
	public void sendMessage(EventDTO event) throws Throwable {
		rabbitTemplate.convertAndSend(event.getTopic(),null,event.getMessage());
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
