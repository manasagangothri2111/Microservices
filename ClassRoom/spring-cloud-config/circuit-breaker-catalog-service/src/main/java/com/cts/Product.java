package com.cts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "circuit_breaker_orders_tbl")
public class Product {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String category;
	private String color;
	private Double price;

}
