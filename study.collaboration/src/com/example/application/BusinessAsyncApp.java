package com.example.application;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.service.BusinessService;

public class BusinessAsyncApp {

	public static void main(String[] args) {
		System.err.println("%s runs main()".formatted(Thread.currentThread().getName()));

		var businessService = new BusinessService();
		System.err.println("Application is just started!");
		businessService.doAsyncBusiness()
		               .thenAcceptAsync(result -> {
		            	   System.err.println("%s runs thenAcceptAsync()".formatted(Thread.currentThread().getName())); 
		            	   System.err.println("Result is %d.".formatted(result));
		               },Executors.newFixedThreadPool(16)
		               );
		System.err.println("Application is done!");
		try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException e) {}
	}

}
