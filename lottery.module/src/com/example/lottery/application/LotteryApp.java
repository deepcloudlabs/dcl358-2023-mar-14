package com.example.lottery.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ServiceLoader;

import com.example.lottery.service.business.StandardLotteryService;
import com.example.random.service.QoS;
import com.example.random.service.QualityLevel;
import com.example.random.service.RandomNumberService;

public class LotteryApp {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		var props = new Properties();
		props.load(new FileInputStream(new File("src","application.properties")));
		var lotteryService = new StandardLotteryService();
		var services = ServiceLoader.load(RandomNumberService.class);
		RandomNumberService randomNumberService = null;
		for (var service: services) {
			var clazz = service.getClass();
			if (clazz.isAnnotationPresent(QoS.class)) {
				var qos = clazz.getAnnotation(QoS.class);
				QualityLevel qualityLevel = QualityLevel.valueOf(props.getProperty("quality.level"));
				if (qos.value().equals(qualityLevel)) {
					randomNumberService = service;
					break;
				}
			}
		}
		lotteryService.setRandomNumberService(randomNumberService );
		lotteryService.draw(60, 6, 10)
		              .forEach(System.out::println);
	}

}
