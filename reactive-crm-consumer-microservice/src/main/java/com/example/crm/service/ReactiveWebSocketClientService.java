package com.example.crm.service;

import java.net.URI;
import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

@Service
public class ReactiveWebSocketClientService {
	private final WebSocketClient client;

	public ReactiveWebSocketClientService(WebSocketClient client) {
		this.client = client;
	}

	@PostConstruct
	public void connect() {
		System.err.println("Trying to connect to the reactive websocket server...");
		client.execute(URI.create("ws://localhost:3500/event-emitter"), session -> {
			System.err.println("Connected to the server!");
			return Mono.empty();
		}).block(Duration.ofMillis(5000));
	}
}
