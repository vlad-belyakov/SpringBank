package org.example.JSON;

import org.example.user.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONWriter {

    public String writeUser(UserClient userClient) {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(userClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
