package com.microservice.alt.models;

public record NadaraResponse(
        String clientTranId,
        String name,
        String fatherName,
        String motherName,
        String address1,
        String address2,
        String placeOfBirth,
        String CNIC
) { }
