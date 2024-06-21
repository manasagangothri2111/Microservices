package com.cts.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.model.Book;

@FeignClient(name = "producer-service", url="http://localhost:9090/")
public interface BookConsumer {
	
	@GetMapping("/book/details")
	public String getBookDetails();
	
	@GetMapping("/book/{bid}")
	public Book  getBookById(@PathVariable Integer bid);
	
	@GetMapping("/book/all")
	public List<Book> getAllBooks();
}
