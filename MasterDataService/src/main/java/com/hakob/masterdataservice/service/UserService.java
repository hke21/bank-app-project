package com.hakob.masterdataservice.service;

import com.hakob.masterdataservice.http.HttpClient;
import com.hakob.masterdataservice.model.User;
import com.hakob.masterdataservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private HttpClient httpClient;

    @Autowired
    private UserRepository userRepository;

    public User storeUserInDb(User user) {
        return userRepository.save(user);
    }

    public Boolean sendOutSmsNotification(String phoneNumber, Map<String, String> headers) {
        return httpClient.postUserToSmsNotificationService(phoneNumber, headers);
    }

    public Boolean checkIfUserExists(String phoneNumber) {
        try {
            userRepository.findUserByPhoneNumber(phoneNumber).orElseThrow(Exception::new);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
