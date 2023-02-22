package com.frmicroservice.customer.services;

import com.frmicroservice.customer.models.Customer;
import com.frmicroservice.customer.models.RegistrationRequest;
import com.frmicroservice.customer.models.Response;
import com.frmicroservice.customer.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Response<String> healthCheck() {
        Response<String> response = new Response<>();
        response.status = true;
        response.message = "Welcome to FaceRecognition Microservice";
        response.response = "This is a demo service to test";
        return response;
    }

    public Response<Customer> registerClient(RegistrationRequest regisReq) {
        Customer customer = Customer.builder().companyEmail(regisReq.companyEmail()).password(regisReq.password()).companyName(regisReq.companyName()).build();
        customerRepository.saveAndFlush(customer);
        Response<Customer> response = new Response<>();
        response.status = true;
        response.message = "Registration Successfully";
        response.response = customer;
        return response;
    }
}
