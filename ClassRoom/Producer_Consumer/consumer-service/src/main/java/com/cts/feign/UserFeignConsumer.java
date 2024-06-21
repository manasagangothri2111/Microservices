package com.cts.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "USER-SERVICE", url = "https://jsonplaceholder.typicode.com")
public interface UserFeignConsumer {

	@GetMapping("/users")
	public String getUsers();

}
