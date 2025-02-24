package org.example.json;

import org.example.entities.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONReader {

    public UserClient readUser(String jsonString) {

        ObjectMapper objectMapper = new ObjectMapper();
        UserClient user = null;

        try {
            user = objectMapper.readValue(jsonString, UserClient.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
