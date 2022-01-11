package com.hakob.customerratingservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserSerializer implements Serializer {

    @Override
    public void configure(Map configs, boolean isKey) {
//        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, Object user) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(user).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
