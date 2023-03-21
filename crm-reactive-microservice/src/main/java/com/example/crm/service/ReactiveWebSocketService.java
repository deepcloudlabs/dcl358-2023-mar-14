package com.example.crm.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ReactiveWebSocketService implements WebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		String sessionId = session.getId();
		System.err.println("New session [%s] is created.".formatted(sessionId));
		sessions.put(sessionId, session);
		session.receive().doFinally(sigType -> {
			switch (sigType) {
			case ON_COMPLETE: {
				System.err.println("Closing the session [%s]".formatted(sessionId));
				sessions.remove(sessionId);
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + sigType);
			}
		});
		return Mono.empty();
	}

	public Mono<Void> sendMessage(String jsonMessage){
		Flux.fromIterable(sessions.entrySet())
		    .parallel()
		    .runOn(Schedulers.boundedElastic())
		    .doOnNext( entry -> {
		    	WebSocketSession session = entry.getValue();
				session.send(Mono.just(session.textMessage(jsonMessage))).subscribe();
		    }).subscribe();
		return Mono.empty();
	}
}
