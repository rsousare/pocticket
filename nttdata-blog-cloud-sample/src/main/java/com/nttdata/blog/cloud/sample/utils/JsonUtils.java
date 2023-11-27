package com.nttdata.blog.cloud.sample.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
