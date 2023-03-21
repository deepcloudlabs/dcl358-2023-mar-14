package com.example.crm.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.domain.CustomerDocument;
import com.example.crm.repository.CustomerDocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveCustomerService {

	private final CustomerDocumentRepository customerDocumentRepository;
	private final ReactiveKafkaProducerTemplate<String, String> reactiveProducerTemplate;
	private final ObjectMapper objectMapper;
	
	public ReactiveCustomerService(CustomerDocumentRepository customerDocumentRepository,
			ReactiveKafkaProducerTemplate<String, String> reactiveProducerTemplate, ObjectMapper objectMapper) {
		this.customerDocumentRepository = customerDocumentRepository;
		this.reactiveProducerTemplate = reactiveProducerTemplate;
		this.objectMapper = objectMapper;
	}

	public Mono<CustomerDocument> findByIdentity(String identity) {
		return customerDocumentRepository.findById(identity);
	}

	public Flux<CustomerDocument> findAll(int pageNo, int pageSize) {
		return customerDocumentRepository.findAll(PageRequest.of(pageNo, pageSize));
	}

	public Mono<CustomerDocument> acquireCustomer(CustomerDocument customer) {
		return customerDocumentRepository.insert(customer);
	}

	public Mono<CustomerDocument> updateCustomer(String identity, CustomerDocument customer) {
		try {
			var customerAsJson = objectMapper.writeValueAsString(customer);
			return customerDocumentRepository.save(customer)
					.doOnNext(
							cust -> reactiveProducerTemplate.send("crm-events",customerAsJson).subscribe(System.out::println)
					);			
		}catch(JsonProcessingException e) {
			System.err.println("Error while converting to json: %s".formatted(e.getMessage()));
		}
		return Mono.empty();
	}

	public Mono<CustomerDocument> removeByIdentity(String identity) {
		return customerDocumentRepository.findById(identity)
				                         .doOnNext(customer -> customerDocumentRepository.delete(customer).subscribe(System.out::println));
	}

}
