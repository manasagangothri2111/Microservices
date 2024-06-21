package com.cts;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1/catalogs")
@EnableDiscoveryClient
@Slf4j
public class CircuiteBreakerCatalogServiceApplication {

	@Autowired
	private ProductRepository repository;
	
	@Value("${greetings.message}")
	private String greetings;
	

	@PostConstruct
	public void initialTableData() {
		repository
				.saveAll(Stream
						.of(new Product(1, "TV", "electronics", "black", 25000.0),
								new Product(2, "laptop", "electronics", "black", 85000.0),
								new Product(3, "Mobile", "electronics", "black", 45000.0),
								new Product(4, "fan", "electronics", "black", 5000.0),
								new Product(5, "mouse", "electronics", "black", 500.0),
								new Product(6, "shirt", "clothings", "black", 5000.0),
								new Product(7, "jeans", "clothings", "black", 5000.0),
								new Product(8, "watch", "electronics", "black", 5000.0),
								new Product(9, "refrigerator", "electronics", "black", 25000.0))
						.collect(Collectors.toList()));
	}

	@GetMapping
	public List<Product> getProducts() {
		log.info("get Products method of CATALOG-SERVICE {}", greetings);
		return repository.findAll();
	}

	@GetMapping("/{category}")
	public List<Product> getProductsByCategory(@PathVariable String category) {
		log.info("getProductsByCategory method of CATALOG-SERVICE {}", greetings);
		return repository.findByCategory(category);
	}

	public static void main(String[] args) {
		SpringApplication.run(CircuiteBreakerCatalogServiceApplication.class, args);
	}
}
