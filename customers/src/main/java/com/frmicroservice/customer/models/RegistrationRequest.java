package com.frmicroservice.customer.models;

public record RegistrationRequest (
        String companyName,
        String companyEmail,
        String password
){}