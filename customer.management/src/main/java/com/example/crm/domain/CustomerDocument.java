package com.example.crm.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="customers")
public class CustomerDocument {
	@Id
	private String identity;
	private String fullName;
	private String sms;
	private String email;
	private List<Address> addresses= new ArrayList<>();
	
	public void addAddress(Address address) {
		if(addresses.contains(address))
			throw new IllegalArgumentException("Address already exists.");
		addresses.add(address);
	}
	
	public void removeAddress(Address address) {
		addresses.remove(address);
	}
}
