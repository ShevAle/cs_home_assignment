package org.example.cs_home_assignment;

import org.example.cs_home_assignment.model.Config;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigParserTest {
    @Test
    public void testParse() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        ConfigParser parser = new ConfigParser();
        Config config = parser.parse(classLoader.getResource("config.json").getPath());
        assertEquals(3, config.columns());
        assertEquals(3, config.rows());
        assertEquals(11, config.symbols().size());
        assertEquals(9, config.probabilities().standard_symbols().size());
        assertEquals(5, config.probabilities().bonus_symbols().symbols().size());
    }
}
