package com.microservice.alt.controllers;

import com.microservice.alt.models.Response;
import com.microservice.alt.services.ALTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("api/v1/translation")
@AllArgsConstructor
public class ALTController {
    private final ALTService soapClient;

    @RequestMapping(value = "/translate", method = RequestMethod.POST)
    public Response<String> translateRequest(HttpServletResponse response) {
        log.info("ALT Service Call");
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        return soapClient.ALTService();
    }

}
