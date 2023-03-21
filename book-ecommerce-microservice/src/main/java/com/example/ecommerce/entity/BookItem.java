package com.example.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="items")
public class BookItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bookItemId;
	private String isbn;
	private double price;
	private int quantity; 
}
