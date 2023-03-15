package com.example.crm.event;

import java.time.LocalDateTime;

import org.apache.kafka.common.Uuid;

public abstract class CustomerBaseEvent {
	private final String eventId = Uuid.randomUuid().toString();
	private final LocalDateTime eventDateTime = LocalDateTime.now();
	private final String customerIdentity;
	private final CustomerEventType eventType;

	public CustomerBaseEvent(CustomerEventType eventType, String customerIdentity) {
		this.customerIdentity = customerIdentity;
		this.eventType = eventType;
	}

	public String getEventId() {
		return eventId;
	}

	public LocalDateTime getEventDateTime() {
		return eventDateTime;
	}

	public String getCustomerIdentity() {
		return customerIdentity;
	}

	public CustomerEventType getEventType() {
		return eventType;
	}

}
