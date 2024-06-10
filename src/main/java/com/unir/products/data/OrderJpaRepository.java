package com.unir.products.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unir.products.model.pojo.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface OrderJpaRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

	List<Order> findByUserName(String userName);
}
