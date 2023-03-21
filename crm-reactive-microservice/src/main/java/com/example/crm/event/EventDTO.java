package com.example.crm.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDTO {
	private String topic;
	private String message;
}
