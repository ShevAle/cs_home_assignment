package org.example.cs_home_assignment;

import org.example.cs_home_assignment.model.Config;
import org.example.cs_home_assignment.model.Output;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMain {
    @Test
    public void doTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        ConfigParser parser = new ConfigParser();
        Config config = parser.parse(classLoader.getResource("config.json").getPath());
        FieldGenerator generator = new FieldGenerator(config);
        List<List<String>> matrix = generator.generate();
        print(matrix);
        CombinationChecker checker = new CombinationChecker(config);
        Output out = checker.check(100, matrix);
        print(out);
    }

    @Test
    public void testSpecificMatrix1() throws IOException {
        List<List<String>> matrix = new ArrayList<>(3);
        matrix.add(List.of("A", "A", "B"));
        matrix.add(List.of("A", "+1000", "B"));
        matrix.add(List.of("A", "A", "B"));
        ClassLoader classLoader = getClass().getClassLoader();

        ConfigParser parser = new ConfigParser();
        Config config = parser.parse(classLoader.getResource("config.json").getPath());
        CombinationChecker checker = new CombinationChecker(config);
        Output out = checker.check(100, matrix);
        print(out);
        assertEquals(26000, out.reward(), 0.001);
    }

    @Test
    public void testSpecificMatrix2() throws IOException {
        List<List<String>> matrix = new ArrayList<>(3);
        matrix.add(List.of("A", "B", "C"));
        matrix.add(List.of("E", "B", "5x"));
        matrix.add(List.of("F", "D", "C"));
        ClassLoader classLoader = getClass().getClassLoader();

        ConfigParser parser = new ConfigParser();
        Config config = parser.parse(classLoader.getResource("config.json").getPath());
        CombinationChecker checker = new CombinationChecker(config);
        Output out = checker.check(100, matrix);
        print(out);
        assertEquals(0, out.reward(), 0.001);
    }

    @Test
    public void testSpecificMatrix3() throws IOException {
        List<List<String>> matrix = new ArrayList<>(3);
        matrix.add(List.of("A", "B", "C"));
        matrix.add(List.of("E", "B", "10x"));
        matrix.add(List.of("F", "D", "B"));
        ClassLoader classLoader = getClass().getClassLoader();

        ConfigParser parser = new ConfigParser();
        Config config = parser.parse(classLoader.getResource("config.json").getPath());
        CombinationChecker checker = new CombinationChecker(config);
        Output out = checker.check(100, matrix);
        print(out);
        assertEquals(25000, out.reward(), 0.001);
    }

    private void print(Object o) {
        System.out.println(o);
    }
}
