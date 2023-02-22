package com.microservice.alt.controllers;

import com.microservice.alt.models.Response;
import com.microservice.alt.services.ALTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/translation")
@AllArgsConstructor
public class ALTController {

    @Autowired
    private final ALTService soapClient;

    @PostMapping(path = "/translate")
    public Response<String> translateRequest() {
        log.info("ALT Service Call");
        return soapClient.ALTService();
    }
}
