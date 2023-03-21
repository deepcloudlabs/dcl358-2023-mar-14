package com.example.crm.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "outbox-events")
@Data
public class EventDocument {
	@Id 
	private String id;
	private String topicName;
	private String event;
}
