package com.intuit.profilevalidationsystem.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Mapper {

    private Mapper() {}

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class cls) throws IOException {
        return (T) objectMapper.readValue(json, cls);
    }

    public static <T> String toJson(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}