package com.alan.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JsonUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public String convertToJson(Object ob) {
        try {
            return objectMapper.writeValueAsString(ob);
        } catch (Exception e) {
            log.error("Error While Convert Object To Json", e);
            return "";
        }
    }
}
