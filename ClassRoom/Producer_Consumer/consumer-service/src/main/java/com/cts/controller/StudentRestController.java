package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.feign.BookConsumer;

@RestController
@RequestMapping("/student")
public class StudentRestController {
	
	@Autowired
	BookConsumer consumer;
	
	@GetMapping("/details")		//http://localhost:9091/student/details
	public String getStudInfo() {
		return "Showing Details of Student Info ==> "+consumer.getBookDetails();
	}
	
	@GetMapping("/getOneBook/{bid}")
	public String getOneBookForStud(@PathVariable Integer bid) {
		return "Accessing from Student Service ==> "+consumer.getBookById(bid);
	}
	
	@GetMapping("/allBooks")
	public String getAllBooksInfo() {
		return "Accessing from Student Service ==> "+consumer.getAllBooks();
	}
}
