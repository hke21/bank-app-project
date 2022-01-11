package com.hakob.customerratingservice.controller;

import com.hakob.customerratingservice.model.User;
import com.hakob.customerratingservice.service.UserService;
import com.hakob.customerratingservice.utils.HttpHeadersInserter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HttpHeadersInserter httpHeadersInserter;

    private int requestCount = 0;
    @PostMapping(path = "/rate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> rateUser(@RequestBody User requestUser, @RequestHeader Map<String, String> headers) throws InterruptedException {
        logger.info("Request count: " + ++requestCount);

        HttpHeaders responseHeaders = httpHeadersInserter.insertResponseTraceHeaders(headers);
        User ratedUser = userService.getRatedUser(requestUser);

        ResponseEntity<User> responseEntity = ResponseEntity.ok()
                .headers(responseHeaders)
                .body(ratedUser);

        return responseEntity;
    }

    private int getRatingCount = 0;

    @GetMapping(path = "/rating/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> rateUser(@PathVariable Long id) throws InterruptedException {
        logger.info("Get rating count: " + ++getRatingCount);


        ResponseEntity<Double> responseEntity = ResponseEntity.ok()
                .body(userService.getUser(id));

        return responseEntity;
    }


    @GetMapping(path = "/health")
    public ResponseEntity health() {
        return ResponseEntity.ok().build();
    }
}
