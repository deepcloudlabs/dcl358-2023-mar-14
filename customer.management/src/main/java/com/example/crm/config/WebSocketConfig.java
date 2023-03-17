package com.example.crm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.crm.service.WebSocketMessagingService;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	private final WebSocketMessagingService webSocketMessagingService;
	
	public WebSocketConfig(WebSocketMessagingService webSocketMessagingService) {
		this.webSocketMessagingService = webSocketMessagingService;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// localhost:7400/crm/api/v1/crm-events
		registry.addHandler(webSocketMessagingService, "/crm-events")
		        .setAllowedOrigins("*");
	}

	
}
