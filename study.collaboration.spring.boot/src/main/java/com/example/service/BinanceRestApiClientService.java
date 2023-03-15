package com.example.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BinanceRestApiClientService {
	private String binanceApiUrl="https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";
	
	@Scheduled(fixedRate = 3_000)
	public void callBinanceRestApi() {
		var rt = new RestTemplate();
		var ticker = rt.getForEntity(binanceApiUrl, String.class)
				       .getBody();
		System.out.println(ticker);
	}
}
