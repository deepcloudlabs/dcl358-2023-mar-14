package com.example.ecommerce.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CancelOrderCommand extends OrderCommand {
	private int userId;
	private int orderId;
}
