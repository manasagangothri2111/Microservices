package com.chefapp.identityservice.feign;

import com.chefapp.identityservice.dto.SignUpRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface userFeign {


    @FeignClient(name = "kafka", url = "http://localhost:8111")

    public interface FoodConsumer {

        @GetMapping(path="/api/v1/messages")
        public ResponseEntity<String> sendMessage(@RequestBody SignUpRequest message);
        /*716429*/


    }
}
