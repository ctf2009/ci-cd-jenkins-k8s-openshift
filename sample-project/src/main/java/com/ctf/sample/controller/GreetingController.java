package com.ctf.sample.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @Value("${greeting}")
    private String greeting;

    @Value("${comment}")
    private String comment;

    @Value("${hostname}")
    private String hostname;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> consumersPauseStatus() {
        final Map<String, Object> response = new HashMap<>();
        response.put("greeting", greeting);
        response.put("comment", comment);
        response.put("hostname", hostname);
        return ResponseEntity.ok(response);
    }

}
