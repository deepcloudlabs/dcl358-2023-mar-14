package com.example.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReactiveCrmConsumerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveCrmConsumerMicroserviceApplication.class, args);
	}

}
