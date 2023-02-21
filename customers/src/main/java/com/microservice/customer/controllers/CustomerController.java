package com.microservice.customer.controllers;

import com.microservice.customer.models.Response;
import com.microservice.customer.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    @GetMapping(path = "/health")
    public Response<String> healthCheck() {
        log.info("Health Check Service ");
       return customerService.healthCheck();
    }

}
