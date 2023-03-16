package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.TickerDTO;

@Service
@ConditionalOnProperty(name = "restClient", havingValue = "sync")
public class BinanceRestApiClientService {
	@Value("${binance.api.url}")
	private String binanceApiUrl;
		
	@Scheduled(fixedRateString =  "${binanceCallRate}")
	public void callBinanceRestApi() {
		var rt = new RestTemplate();
		var ticker = rt.getForEntity(binanceApiUrl, TickerDTO.class)
				       .getBody();
		System.out.println(ticker);
	}
}
