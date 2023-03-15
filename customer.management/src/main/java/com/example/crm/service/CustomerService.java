package com.example.crm.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.domain.Address;
import com.example.crm.domain.CustomerDocument;
import com.example.crm.repository.CustomerDocumentRepository;

@Service
public class CustomerService {
	private final CustomerDocumentRepository custRepo;
	private final KafkaTemplate<String,String> kafkaTemplate;
	
	public CustomerService(CustomerDocumentRepository custRepo, KafkaTemplate<String, String> kafkaTemplate) {
		this.custRepo = custRepo;
		this.kafkaTemplate = kafkaTemplate;
	}

	public List<CustomerDocument> findAll(int pageNo, int pageSize) {
		return custRepo.findAll(PageRequest.of(pageNo, pageSize)).getContent();
	}

	public CustomerDocument findByIdentity(String identity) {
		return custRepo.findById(identity)
				       .orElseThrow(() -> new IllegalArgumentException("Cannot find customer."));
	}

	public CustomerDocument acquireCustomer(CustomerDocument customer) {
		return custRepo.insert(customer);
	}

	@SuppressWarnings("unchecked")
	public CustomerDocument patchCustomer(String identity, Map<String, Object> changes) {
		var customer = custRepo.findById(identity).orElseThrow(() -> new IllegalArgumentException("Cannot find customer."));
		changes.entrySet().forEach(entry -> {
			var fieldName = entry.getKey();
			switch(fieldName) {
				case "addresses" -> {
					var rawAddresses = (List<LinkedHashMap<String,Object>>) entry.getValue();
					var addresses = new ArrayList<Address>();
					for (LinkedHashMap<String,Object> rawAddress : rawAddresses) {
						var address = new Address();
						address.setAddressId(rawAddress.get("addressId").toString());
						address.setCity(rawAddress.get("city").toString());
						address.setCountry(rawAddress.get("country").toString());
						address.setStreet(rawAddress.get("street").toString());
						address.setZipCode(rawAddress.get("zipCode").toString());
						addresses.add(address);
					}
					//TODO: for each address emit an event!
					customer.setAddresses(addresses);
				}
			}
		});
		custRepo.save(customer);
		return customer;
	}

}
