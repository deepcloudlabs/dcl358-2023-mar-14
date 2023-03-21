package com.example.crm.event;

import java.util.List;

import com.example.crm.domain.Address;

public class CustomerAddressChangedEvent extends CustomerBaseEvent {
	private final List<Address> oldAddresses;
	private final List<Address> newAddresses;

	public CustomerAddressChangedEvent(String customerIdentity, List<Address> oldAddresses,
			List<Address> newAddresses) {
		super(CustomerEventType.CUSTOMER_ADDRESS_CHANGED_EVENT, customerIdentity);
		this.oldAddresses = oldAddresses;
		this.newAddresses = newAddresses;
	}

	public List<Address> getOldAddresses() {
		return oldAddresses;
	}

	public List<Address> getNewAddresses() {
		return newAddresses;
	}

}
