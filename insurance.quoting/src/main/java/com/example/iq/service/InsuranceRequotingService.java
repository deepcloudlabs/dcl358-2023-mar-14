package com.example.iq.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InsuranceRequotingService {

	@KafkaListener(topics="${customerEventTopic}")
	public void listenCustomerEvents(String event) {
		System.out.println(event);
	}
}
