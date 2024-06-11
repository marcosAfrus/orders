package com.unir.products.service;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.unir.products.data.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unir.products.model.pojo.Order;
import com.unir.products.model.request.CreateOrderRequest;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public List<Order> getOrders(String userName, String status) {

		if (StringUtils.hasLength(userName) ||
				StringUtils.hasLength(status)) {
			return repository.search(userName, status);
		}

		List<Order> orders = repository.getOrders();
		return orders.isEmpty() ? null : orders;
	}

	@Override
	public Order getOrder(String productId) {
		return repository.getById(Long.valueOf(productId));
	}


	@Override
	public Order createOrder(CreateOrderRequest request) {

		//Otra opcion: Jakarta Validation: https://www.baeldung.com/java-validation
		if (request != null &&
			request.getUserName() != null &&
			request.getStatus() != null &&
			request.getTotalPay() != null) {

			log.info("Request received for order {}", request.userName);
			Order order = Order.builder()
					.userName(request.getUserName())
					.status(request.getStatus())
					.totalPay(request.getTotalPay())
					.addressDeliver(request.getAddressDeliver())
					.build();
			return repository.save(order);
		} else {
			return null;
		}
	}

	@Override
	public Order updateOrder(String productId, String updateRequest) {
		return null;
	}

}
