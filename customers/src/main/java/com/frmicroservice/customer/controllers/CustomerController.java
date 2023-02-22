package com.frmicroservice.customer.controllers;

import com.frmicroservice.customer.models.Customer;
import com.frmicroservice.customer.models.RegistrationRequest;
import com.frmicroservice.customer.services.CustomerService;
import com.frmicroservice.customer.models.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping(path = "/register")
    public Response<Customer> registerClient(RegistrationRequest data) {
        log.info("New client registration , {}", data);
        return customerService.registerClient(data);
    }

}
