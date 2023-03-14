package com.example.lottery.application;

import java.util.ServiceLoader;

import com.example.lottery.service.business.StandardLotteryService;
import com.example.random.service.RandomNumberService;

public class SimpleLotteryApp {

	public static void main(String[] args) {
		var lotteryService = new StandardLotteryService();
		var services = ServiceLoader.load(RandomNumberService.class);
		RandomNumberService randomNumberService = null;
		var iter = services.iterator();
		iter.next();
		randomNumberService = iter.next();
		lotteryService.setRandomNumberService(randomNumberService );
		lotteryService.draw(60, 6, 10)
		              .forEach(System.out::println);
	}

}
