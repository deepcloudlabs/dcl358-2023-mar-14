package com.example.application;

import com.example.service.BusinessService;

public class BusinessApp {

	public static void main(String[] args) {
		var businessService = new BusinessService();
		System.err.println("Application is just started!");
		try {
			System.err.println(businessService.doBusiness());
		} catch (Exception e) {
			System.err.println("Error while running business: %s".formatted(e.getMessage()));
		}
		System.err.println("Application is done!");
	}

}
