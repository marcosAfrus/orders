package com.unir.products.model.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@Column(name = "userName", nullable = false)
	private String userName;
	
	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "totalPay", nullable = false)
	private Double totalPay;

	@Column(name = "addressDeliver", nullable = true)
	private String addressDeliver;

	@ElementCollection
	@Column(name = "products")
	private List<String> products;
}
