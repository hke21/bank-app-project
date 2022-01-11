package com.hakob.masterdataservice.controller;

import com.hakob.masterdataservice.model.User;
import com.hakob.masterdataservice.service.UserService;
import com.hakob.masterdataservice.utils.HttpHeadersInserter;
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

    @PostMapping(path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> storeUser(@RequestBody User user, @RequestHeader Map<String, String> headers) {
        logger.info("Incoming user to be stored: " + user);
        userService.storeUserInDb(user);
        userService.sendOutSmsNotification(user.getPhoneNumber(), headers);

        HttpHeaders responseHeaders = httpHeadersInserter.insertResponseTraceHeaders(headers);

        ResponseEntity<User> responseEntity = ResponseEntity.ok()
                .headers(responseHeaders)
                .body(user);

        return responseEntity;
    }

    @GetMapping(path = "/health")
    public ResponseEntity health() {
        return ResponseEntity.ok().build();
    }


}
