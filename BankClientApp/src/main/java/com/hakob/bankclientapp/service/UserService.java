package com.hakob.bankclientapp.service;

import com.hakob.bankclientapp.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.hakob.bankclientapp.model.User;

import java.util.Map;


@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private HttpClient httpClient;

    public UserService() {
    }

    public ResponseEntity<Boolean> validateUserRegistration(User user) {
        var r = httpClient.postUserToValidationService(user);
        return r;
    }
    public ResponseEntity<Boolean> validateUserRegistrationWithPossibleError(User user) {
        var r = httpClient.postUserToValidationServiceWithPossibleError(user);
        return r;
    }

    public ResponseEntity<Boolean> validateUserRegistrationWithError(User user) {
        var r = httpClient.postUserToValidationServiceWithError(user);
        return r;
    }

    public ResponseEntity<User> rateUserAndStore(User user, Map<String, String> headers) {
        return httpClient.postUserToRiskRaitingServiceAndStore(user, headers);
    }
}
