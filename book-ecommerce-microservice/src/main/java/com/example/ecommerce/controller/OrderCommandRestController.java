package com.example.ecommerce.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.CancelOrderResponse;
import com.example.ecommerce.dto.PlaceOrderResponse;
import com.example.ecommerce.entity.PlaceOrderCommand;
import com.example.ecommerce.service.OrderCommandService;

@RestController
@RequestMapping("commands")
@ConditionalOnProperty(name="enable.write",havingValue = "true")
public class OrderCommandRestController {
	private final OrderCommandService orderCommandService;

	public OrderCommandRestController(OrderCommandService orderCommandService) {
		this.orderCommandService = orderCommandService;
	}

	@PostMapping
	public PlaceOrderResponse placeOrder(@RequestBody PlaceOrderCommand placeOrderCommand) throws Exception {
		orderCommandService.placeOrder(placeOrderCommand.getUserId(), placeOrderCommand.getItems());
		return new PlaceOrderResponse("success");
	}
	
	@DeleteMapping(value="{orderId}",params = "userId")
	public CancelOrderResponse placeOrder(@PathVariable int orderId,@RequestParam int userId) throws Exception {
		orderCommandService.cancelOrder(userId,orderId);
		return new CancelOrderResponse("success");
	}
}
