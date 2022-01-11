package com.hakob.bankclientapp.controller;

import com.hakob.bankclientapp.model.User;
import com.hakob.bankclientapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {
        logger.info("Create user payload: \n" + user);

        ResponseEntity<Boolean> response = null;
        if (user.getPhoneNumber() != null) {
            response = userService.validateUserRegistration(user);
            if(!response.getStatusCode().equals(HttpStatus.OK)) {
                logger.info("Response from user-validator-service is not 200, redirecting that status code to the client");

                return ResponseEntity.status(response.getStatusCode()).build();
            }
            if (Boolean.FALSE.equals(response.getBody())) {
                logger.info("Validation failed");
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
            }
        }
//        logger.info("User does not exists and can be registered");
        ResponseEntity<User> userRatedAndStored = userService.rateUserAndStore(user, response.getHeaders().toSingleValueMap());

        if (userRatedAndStored.getStatusCode().is5xxServerError()) {
            logger.info("Exception occured, responding with 5xx status code");
            return userRatedAndStored;
        }

//        logger.info("Response from Validator service HEADERS toSingleValueMap: " + response.getHeaders().toSingleValueMap());

//        if (userRatedAndStored.getRating() != null) {
//            logger.info("User rated and stored");
//        }

        logger.info("Everything went smooth, returning 200 to the client");
        return userRatedAndStored;
    }

    @PostMapping(path = "/users-possible-bad-gateway",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithPossibleError(@RequestBody User user) {
        logger.info("Create user payload with possible error: \n" + user);

        ResponseEntity<Boolean> response = null;
        if (user.getPhoneNumber() != null) {
            response = userService.validateUserRegistrationWithPossibleError(user);
            if(!response.getStatusCode().equals(HttpStatus.OK)) {
                logger.info("Response from user-validator-service is not 200, redirecting that status code to the client");

                return ResponseEntity.status(response.getStatusCode()).build();
            }
            logger.info("Response from Validator service HEADERS: " + response.getHeaders());
            if (Boolean.FALSE.equals(response.getBody())) {
                logger.info("Validation failed");
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
            }
        }
//        logger.info("User does not exists and can be registered");
        ResponseEntity<User> userRatedAndStored = userService.rateUserAndStore(user, response.getHeaders().toSingleValueMap());
//        logger.info("Response from Validator service HEADERS toSingleValueMap: " + response.getHeaders().toSingleValueMap());

//        if (userRatedAndStored.getRating() != null) {
//            logger.info("User rated and stored");
//        }

        logger.info("Everything went smooth, returning 200 to the client");
        return userRatedAndStored;
    }

    private int badgatewayCounter = 0;

    @PostMapping(path = "/users-bad-gateway",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithError(@RequestBody User user) {
        logger.info("Bad gateway counter: " + ++badgatewayCounter);
        logger.info("Request in bad-gateway endpoint");

        ResponseEntity<Boolean> response = null;
        if (user.getPhoneNumber() != null) {
            response = userService.validateUserRegistrationWithError(user);
            if(!response.getStatusCode().equals(HttpStatus.OK)) {
                logger.info("Responsing to the request with the bad gateway status code");

                return ResponseEntity.status(response.getStatusCode()).build();
            }
            if (Boolean.TRUE.equals(response.getBody())) {
                logger.info("User exists!");
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
            }
        }
//        logger.info("User does not exists and can be registered");
        ResponseEntity<User> userRatedAndStored = userService.rateUserAndStore(user, response.getHeaders().toSingleValueMap());
//        logger.info("Response from Validator service HEADERS toSingleValueMap: " + response.getHeaders().toSingleValueMap());

//        if (userRatedAndStored.getRating() != null) {
//            logger.info("User rated and stored");
//        }

        logger.info("Everything went smooth, returning 200 to the client");
        return ResponseEntity.ok(user);
    }

    private int counterGet = 0;
    @GetMapping(path = "/get-test",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getTest() {
        logger.info("Coutner get: " + ++counterGet);
        return ResponseEntity.ok(Boolean.TRUE);
    }


    @GetMapping(path = "/health")
    public ResponseEntity health() {
        return ResponseEntity.ok().build();
    }

}
