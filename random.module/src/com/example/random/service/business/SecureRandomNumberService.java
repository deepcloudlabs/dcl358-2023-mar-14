package com.example.random.service.business;

import java.security.SecureRandom;
import java.util.Random;

import com.example.random.service.QoS;
import com.example.random.service.QualityLevel;
import com.example.random.service.RandomNumberService;

@QoS(QualityLevel.SECURE)
public class SecureRandomNumberService implements RandomNumberService {
	private final Random random = new SecureRandom();

	public SecureRandomNumberService() {
		System.err.println("SecureRandomNumberService::SecureRandomNumberService()");
	}

	@Override
	public int generate(int min, int max) {
		System.err.println("SecureRandomNumberService::generate");
		return random.nextInt(min, max);
	}

}
