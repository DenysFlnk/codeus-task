package com.practice.assignment.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomMapperUtils {
    public static User mapToUser(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        try {
            ResponseWrapper responseWrapper = objectMapper.readValue(json, ResponseWrapper.class);
            user = responseWrapper.data();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
