package com.example.crm.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.crm.event.EventDTO;

@Service
public class MessagingService {
	private final ApplicationEventPublisher publisher;

	public MessagingService(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void sendMessage(String topic, String message) throws Throwable {
		publisher.publishEvent(new EventDTO(topic,message));
	}
}
