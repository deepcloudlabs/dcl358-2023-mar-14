package com.example.iq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class InsuranceRequotingRabbitMQService {

	@RabbitListener(queues = "${customerEventQueue}")
	public void listenCustomerEvents(String event) {
		System.out.println(event);
	}
}
