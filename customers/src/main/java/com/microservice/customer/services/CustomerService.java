package com.microservice.customer.services;

import com.microservice.customer.models.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
//    private final CustomerRepository;

    public Response<String> healthCheck() {
        Response<String> response = new Response<>();
        response.status = true;
        response.message = "Welcome to FaceRecognition Microservice";
        response.response = "This is a demo service to test";
        return response;

    }
}
