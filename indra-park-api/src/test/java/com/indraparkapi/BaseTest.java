package com.indraparkapi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public abstract class BaseTest {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
