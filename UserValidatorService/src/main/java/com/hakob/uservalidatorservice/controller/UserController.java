package com.hakob.uservalidatorservice.controller;

import com.google.gson.JsonObject;
import com.hakob.uservalidatorservice.Utils.HttpHeadersInserter;
import com.hakob.uservalidatorservice.model.User;
import com.hakob.uservalidatorservice.service.UserValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
public class UserController {
    private int counter = 0;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserValidationService userValidationService;

    @Autowired
    private HttpHeadersInserter httpHeadersInserter;

    private int counterValidate = 0;

    @PostMapping(path = "/validate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validateUser(@RequestBody Map<String, String> payload, @RequestHeader Map<String, String> headers) throws InterruptedException {
        logger.info("Validate user request count " + ++counter);
        logger.info("Working version: V2");

        logger.info("Validate counter " + ++counterValidate);

//      Timeout
//        Thread.sleep(Long.parseLong("5000"));

        boolean valid = userValidationService.validateUserRegistration(payload);

        HttpHeaders responseHeaders = httpHeadersInserter.insertResponseTraceHeaders(headers);

        ResponseEntity<Boolean> responseEntity = ResponseEntity.ok()
                .headers(responseHeaders)
                .body(valid);

        return responseEntity;
    }

    private int counterBadGateway = 0;
    //50 percent chance of bad gateway error result
    @PostMapping(path = "/validate-possible-bad-gateway",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validateWithBadGatewayPossibility(@RequestBody Map<String, String> payload, @RequestHeader Map<String, String> headers) throws InterruptedException {
        logger.info("Possible 502 BAD_GATEWAY Request count " + ++counter);

        if (new Random().nextInt(2) == 0) {
            logger.info("Responding with BAD_GATEWAY");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }

        logger.info("Responding with 200 OK");

        boolean valid = userValidationService.validateUserRegistration(payload);

        HttpHeaders responseHeaders = httpHeadersInserter.insertResponseTraceHeaders(headers);

        ResponseEntity<Boolean> responseEntity = ResponseEntity.ok()
                .headers(responseHeaders)
                .body(valid);

        return responseEntity;
    }

    // 100% error
    @PostMapping(path = "/validate-bad-gateway",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validateWithBadGateway(@RequestBody Map<String, String> payload, @RequestHeader Map<String, String> headers) throws InterruptedException {
        logger.info("Request count " + ++counter);
        logger.info("Incoming request in bad gateway endpoint: \n" + payload);

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

    private int getCounter = 0;

    @GetMapping(path = "/get-test",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getTestEndpoint() throws InterruptedException {
        logger.info("Get count: " + ++getCounter);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping(path = "/health")
    public ResponseEntity health() {
        return ResponseEntity.ok().build();
    }
}
