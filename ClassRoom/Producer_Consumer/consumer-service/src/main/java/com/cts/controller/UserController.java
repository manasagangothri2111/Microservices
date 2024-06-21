package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.feign.UserFeignConsumer;

@RestController
@RequestMapping("/test")
public class UserController {

	@Autowired
	UserFeignConsumer consumer;

	@GetMapping("/all")
	public String users() {
		return "fetching users info : " + consumer.getUsers();
	}

}
