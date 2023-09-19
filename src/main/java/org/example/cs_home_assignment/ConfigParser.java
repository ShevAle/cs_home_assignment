package org.example.cs_home_assignment;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cs_home_assignment.model.Config;

import java.io.File;
import java.io.IOException;

public class ConfigParser {
    public Config parse(String filePath) throws IOException {
        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(file, Config.class);
    }
}
