package com.example.ecommerce.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class PlaceOrderCommand extends OrderCommand {
	private int userId;
	@OneToMany
	private List<BookItem> items;
}
