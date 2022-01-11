package com.hakob.smsnotificationservice.controller;

import com.hakob.smsnotificationservice.service.SmsNotificationService;
import com.hakob.smsnotificationservice.utils.HttpHeadersInserter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SmsNotificationService service;

    @Autowired
    private HttpHeadersInserter httpHeadersInserter;

    private int counter = 0;

    @PostMapping(path = "/notifyUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendSmsNotification(@RequestBody Map<String, String> payload, @RequestHeader Map<String, String> headers) {
        HttpHeaders responseHeaders = httpHeadersInserter.insertResponseTraceHeaders(headers);

        logger.info("Request counter: " + ++counter);
        if (service.sendSmsNotification(payload.get("phoneNumber"))) {
            return new ResponseEntity(responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
