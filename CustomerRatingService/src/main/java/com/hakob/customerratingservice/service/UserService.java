package com.hakob.customerratingservice.service;

import com.hakob.customerratingservice.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private Map<String, Map<String, Double>> weightings = new HashMap<>();

    private Map<User, Double> userRatings = new HashMap<>();

    public User getRatedUser(User user) {
        user.setRating(rateUser(user));
        return user;
    }

    public Double rateUser(User user) {
        weightPhoneNumberCountryCode(user.getPhoneNumber());
        weightings.put("countryCode", Map.of("+43", 0.01, "+374", 0.03, "+001", 0.05));

        Double rating = 0.0;
//        raiting += weighPhoneNumberCountryCode(user.getPhoneNumber());
        String countryCode = weightPhoneNumberCountryCode(user.getPhoneNumber());
        Double phoneNumberWeighting = weightings.get("countryCode").get(countryCode);
        rating = 1.0;
        return rating;
    }

    public Double getUser(Long id) {
        return 1.0;
    }

    private String weightPhoneNumberCountryCode(String phoneNumber) {
        return phoneNumber.substring(0, 3);
    }
}
