package com.example.crm.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.domain.CustomerDocument;
import com.example.crm.service.ReactiveCustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
@CrossOrigin
@Validated
public class CrmReactiveRestController {
	private final ReactiveCustomerService reactiveCustomerService;

	public CrmReactiveRestController(ReactiveCustomerService reactiveCustomerService) {
		this.reactiveCustomerService = reactiveCustomerService;
	}

	@GetMapping("{identity}")
	public Mono<CustomerDocument> findCustomerByIdentity(@PathVariable String identity) {
		return reactiveCustomerService.findByIdentity(identity);
	}

	@GetMapping(params = { "pageNo", "pageSize" })
	public Flux<CustomerDocument> findCustomers(@RequestParam int pageNo, @RequestParam int pageSize) {
		return reactiveCustomerService.findAll(pageNo, pageSize);
	}

	@PostMapping
	public Mono<CustomerDocument> addCustomer(@RequestBody CustomerDocument customer) {
		return reactiveCustomerService.acquireCustomer(customer);
	}

	@PutMapping("{identity}")
	public Mono<CustomerDocument> updateCustomer(@PathVariable String identity,
			@RequestBody CustomerDocument customer) {
		return reactiveCustomerService.updateCustomer(identity, customer);
	}
	
	@DeleteMapping("{identity}")
	public Mono<CustomerDocument> removeCustomerByIdentity(@PathVariable String identity) {
		return reactiveCustomerService.removeByIdentity(identity);
	}
}
