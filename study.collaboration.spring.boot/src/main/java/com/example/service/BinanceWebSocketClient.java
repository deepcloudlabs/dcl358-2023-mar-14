package com.example.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import com.example.dto.TradeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BinanceWebSocketClient implements WebSocketHandler{
	private final WebSocketClient webSocketClient;
	private final ObjectMapper objectMapper;
	private final String binanceWsUrl;
	
	public BinanceWebSocketClient(
			WebSocketClient webSocketClient,
			ObjectMapper objectMapper,
			@Value("${binance.wss.url}") String binanceWsUrl) {
		this.webSocketClient = webSocketClient;
		this.binanceWsUrl = binanceWsUrl;
		this.objectMapper = objectMapper;
	}

	@PostConstruct
	public void connectToBinance() {
		webSocketClient.doHandshake(this, binanceWsUrl);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.err.println("Successfully connected to the binance ws server.");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		var payload = message.getPayload().toString();
		var trade = objectMapper.readValue(payload, TradeDTO.class);
		System.err.println(trade);
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
