package com.example.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class BusinessService {

	public int doBusiness() throws BusinessException, InterruptedException {
		TimeUnit.SECONDS.sleep(5);
		if(ThreadLocalRandom.current().nextBoolean())
			throw new BusinessException("Something is wrong!");
		return 42;
	}
	
	public CompletableFuture<Integer> doAsyncBusiness()  {
		return CompletableFuture.supplyAsync(()->{
			System.err.println("%s runs doAsyncBusiness()".formatted(Thread.currentThread().getName()));
			try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {}
			return 42;			
		},Executors.newFixedThreadPool(16));
	}
}
