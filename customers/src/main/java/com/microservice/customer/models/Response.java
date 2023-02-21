package com.microservice.customer.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Response<T> {
    public boolean status;
    public String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T response;
}
