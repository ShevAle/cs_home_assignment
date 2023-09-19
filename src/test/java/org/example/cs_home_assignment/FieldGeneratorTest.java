package org.example.cs_home_assignment;

import org.example.cs_home_assignment.model.Config;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldGeneratorTest {
    @Test
    public void testGenerate() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        ConfigParser parser = new ConfigParser();
        Config config = parser.parse(classLoader.getResource("config.json").getPath());
        FieldGenerator generator = new FieldGenerator(config);
        List<List<String>> field = generator.generate();
        assertEquals(3, field.size());
        assertEquals(3, field.get(1).size());
    }
}
