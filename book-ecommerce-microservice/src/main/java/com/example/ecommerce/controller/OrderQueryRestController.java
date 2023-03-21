package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.document.OrderStatistics;
import com.example.ecommerce.entity.BookItem;
import com.example.ecommerce.service.OrderQueryService;

@RestController
@ConditionalOnProperty(name="enable.read",havingValue = "true")
public class OrderQueryRestController {
	private final OrderQueryService orderQueryService;

	public OrderQueryRestController(OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}

	@GetMapping("/orders/{orderId}")
	public List<BookItem> getOrder(@PathVariable int orderId) throws Exception {
		return orderQueryService.getOrder(orderId);
	}
	
	@GetMapping("statistics")
	public OrderStatistics placeOrder(@PathVariable int orderId) throws Exception {
		return orderQueryService.getOrderStatistics();
	}
}
