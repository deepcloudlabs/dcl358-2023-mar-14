package com.example.crm.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

@Configuration
public class WebSocketConfig {

	@Bean
	HandlerMapping webSocketHandlerMapping(WebSocketHandler webSocketHandler) {
		System.err.println("Configuring webSocketHandlerMapping...");
	    Map<String, WebSocketHandler> map = new HashMap<>();
	    map.put("/crm-events", webSocketHandler);

	    SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
	    handlerMapping.setOrder(1);
	    handlerMapping.setUrlMap(map);
	    return handlerMapping;
	}
}
