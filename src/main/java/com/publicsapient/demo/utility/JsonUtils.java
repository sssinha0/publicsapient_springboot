package com.publicsapient.demo.utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String convertListToJson(List<String> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            // log and return empty JSON array
            return "[]";
        }
    }
}
