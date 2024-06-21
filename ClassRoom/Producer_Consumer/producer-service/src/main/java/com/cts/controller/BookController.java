package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.Book;

/**
 * GET Details: http://localhost:9091/book/details
 */

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	Environment environment;
	
	@GetMapping("/details")
	public String getBookDetails() {
		return "Producer-Service running on: "+environment.getProperty("server.port");
	}

	@GetMapping("/{bid}")
	public Book getBookById(@PathVariable Integer bid) {
		return new Book(bid, "master spring boot", "rod johnson");
	}

	@GetMapping("/all")
	public List<Book> getBooks() {
		return List.of(new Book(101, "head first java", "Author1"), new Book(202, "Learn Python", "Author2"),
				new Book(303, "Spring in Action", "Author3"));
	}
}
