package com.hakob.uservalidatorservice.service;

import com.hakob.uservalidatorservice.http.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserValidationService {
    public boolean validateUserRegistration(Map<String, String> user) {
        if (user.get("phoneNumber").length() == 11)
            return true;
        else
            return false;
    }
}
