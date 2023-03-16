package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;

import com.example.dto.TickerDTO;

@Service
@ConditionalOnProperty(name = "restClient", havingValue = "async")
@SuppressWarnings("deprecation")
public class BinanceRestApiAsyncClientService {
	@Value("${binance.api.url}")
	private String binanceApiUrl;
		
	@Scheduled(fixedRateString =  "${binanceCallRate}")
	public void callBinanceRestApi() {
		System.err.println("callBinanceRestApi::started");
		var rt = new AsyncRestTemplate();
		rt.getForEntity(binanceApiUrl, TickerDTO.class)
		  .addCallback(
			ticker -> System.err.println(ticker.getBody()), 
			error -> System.err.println(error.getMessage()) 
		);
		System.err.println("callBinanceRestApi::completed");
	}
}
