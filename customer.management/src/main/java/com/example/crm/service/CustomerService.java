package com.example.crm.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.domain.Address;
import com.example.crm.domain.CustomerDocument;
import com.example.crm.event.CustomerAddressChangedEvent;
import com.example.crm.repository.CustomerDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerService {
	private final CustomerDocumentRepository custRepo;
	private final String customerEventTopic= "crm-events";
	private final ObjectMapper objectMapper;
	private final MessagingService messagingService;
	
	public CustomerService(CustomerDocumentRepository custRepo, 
			MessagingService messagingService,
			ObjectMapper objectMapper) {
		this.custRepo = custRepo;
		this.messagingService = messagingService;
		this.objectMapper = objectMapper;
		System.err.println(this.messagingService.getClass().getName());
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
					try {
						var newAddresses = extractAddresses(entry);
						var oldAddresses = customer.getAddresses();
						customer.setAddresses(newAddresses);
						var event = new CustomerAddressChangedEvent(identity,oldAddresses,newAddresses);
						var eventAsJson = objectMapper.writeValueAsString(event); 
						messagingService.sendMessage(customerEventTopic,eventAsJson);
					}catch (Throwable e) {
						System.err.println("Error has occurred while converting to json: %s".formatted(e.getMessage()));
					} 
				}
			}
		});
		custRepo.save(customer);
		return customer;
	}

	private ArrayList<Address> extractAddresses(Entry<String, Object> entry) {
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
		return addresses;
	}

}
