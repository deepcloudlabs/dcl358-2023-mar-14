package com.example.crm.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.crm.event.EventDTO;

@Service
public class WebSocketMessagingService implements WebSocketHandler {
	private final Map<String,WebSocketSession> sessions = new ConcurrentHashMap<>();
	
	@EventListener
	public void sendMessage(EventDTO event) throws Throwable {
		sessions.values()
		        .forEach(session -> {
		        	try {
						session.sendMessage(new TextMessage(event.getMessage().getBytes()));
					} catch (IOException e) {
						System.err.println("Error while sending ws message: %s".formatted(e.getMessage()));
					}
		        });
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String sessionId = session.getId();
		sessions.put(sessionId, session);
		System.err.println("New WS Connection is created: %s".formatted(sessionId));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		System.err.println("A problem has occurred: %s".formatted(e.getMessage()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		var sessionId = session.getId();
		sessions.remove(sessionId);
		System.err.println("WS Connection is closed: %s".formatted(sessionId));
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
