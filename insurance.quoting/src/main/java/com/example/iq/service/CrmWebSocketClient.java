package com.example.iq.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
public class CrmWebSocketClient implements WebSocketHandler{
	private final WebSocketClient webSocketClient = new StandardWebSocketClient();
	private final String crmWsUrl;
	
	public CrmWebSocketClient(@Value("${crmWsUrl}") String crmWsUrl) {
		this.crmWsUrl = crmWsUrl;
	}

	@PostConstruct
	public void connectToBinance() {
		webSocketClient.doHandshake(this, crmWsUrl);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.err.println("Successfully connected to the binance ws server.");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		var payload = message.getPayload().toString();
		System.err.println("New message has arrived from WS Server: %s".formatted(payload));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		System.err.println("An error has occurred: %s".formatted(e.getMessage()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		System.err.println("Disconnected from the binance ws server.");
		
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
