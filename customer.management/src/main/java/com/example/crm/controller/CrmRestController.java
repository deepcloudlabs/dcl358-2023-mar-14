package com.example.crm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.crm.domain.CustomerDocument;
import com.example.crm.service.CustomerService;

@RestController
@RequestMapping("customers")
@RequestScope
@CrossOrigin
@Validated
public class CrmRestController {
	private final CustomerService customerService;

	public CrmRestController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("{identity}")
	public CustomerDocument findCustomerByIdentity(@PathVariable String identity) {
		return customerService.findByIdentity(identity);
	}

	@GetMapping(params = { "pageNo", "pageSize" })
	public List<CustomerDocument> findCustomers(@RequestParam int pageNo, @RequestParam int pageSize) {
		return customerService.findAll(pageNo, pageSize);
	}

	@PostMapping
	public CustomerDocument addCustomer(@RequestBody CustomerDocument customer) {
		return customerService.acquireCustomer(customer);
	}
	@PatchMapping("{identity}")
	public CustomerDocument patchCustomer(@PathVariable String identity,@RequestBody Map<String,Object> changes) {
		return customerService.patchCustomer(identity,changes);
	}
}
