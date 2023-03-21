package com.example.crm.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.crm.dto.TickerDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ReactiveBinanceRestClient {
	private final WebClient webClient= // webflux
			WebClient.builder().baseUrl("https://api.binance.com/api/v3/ticker").build();
			
	private final List<String> symbols = List.of("BTCUSDT", "LTCBTC", "BNBBTC", "NEOBTC", "EOSETH");
	private final Comparator<TickerDTO> BY_PRICE = 
			(t1,t2) -> Double.parseDouble(t1.getPrice()) <= Double.parseDouble(t2.getPrice()) ? -1 : +1;
			
	@Scheduled(fixedRate = 2_000)
	public void callBinanceRestApi() {
		Flux.fromIterable(symbols)
		    .parallel()
		    .runOn(Schedulers.boundedElastic())
		    .flatMap(this::getTicker)
		    .sorted(BY_PRICE)
		    .subscribe(System.out::println);
	}
	
	public Mono<TickerDTO> getTicker(String symbol){
		return webClient.get().uri(uriBuilder -> uriBuilder.path("/price").queryParam("symbol", symbol).build())
			            .retrieve()
			            .bodyToMono(TickerDTO.class);
	}
}
