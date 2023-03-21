package com.example.crm.service;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

@Service
public class ReactiveBinanceWebSocketClientService {
	private final WebSocketClient client;

	public ReactiveBinanceWebSocketClientService(WebSocketClient client) {
		this.client = client;
	}

	@PostConstruct
	public void connect() {
		System.err.println("Trying to connect to the reactive websocket server...");
		client.execute(URI.create("wss://stream.binance.com:9443/ws/btcusdt@trade"), session -> {
			System.err.println("Connected to the server!");
			session.receive().map(WebSocketMessage::getPayloadAsText).doOnNext(trade -> System.err.println(trade)).then().subscribe();
			return Mono.empty();
		}).doOnEach(signal -> System.out.println(signal)).block();
	}
}
