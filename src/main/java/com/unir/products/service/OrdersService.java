package com.unir.products.service;

import java.util.List;

import com.unir.products.model.pojo.Order;
import com.unir.products.model.request.CreateOrderRequest;

public interface OrdersService {
	
	List<Order> getOrders(String userName, String status);
	
	Order getOrder(String orderId);

	Order createOrder(CreateOrderRequest request);

	Order updateOrder(String productId, String updateRequest);

	Order cancelOrder(String productId);
}
