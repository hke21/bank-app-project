package com.hakob.smsnotificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService {
    private Logger logger = LoggerFactory.getLogger(SmsNotificationService.class);

    public boolean sendSmsNotification(String phoneNumber) {
        logger.info("Notification sent to " + phoneNumber);
        return true;
    }
}
