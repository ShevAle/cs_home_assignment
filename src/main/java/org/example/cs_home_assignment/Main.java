package org.example.cs_home_assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cs_home_assignment.model.Config;
import org.example.cs_home_assignment.model.Output;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //we can use cli app library like picocli to properly parse input
        String configFile = args[1];
        int bet = Integer.parseInt(args[3]);

        ConfigParser parser = new ConfigParser();
        Config config = parser.parse(configFile);
        FieldGenerator generator = new FieldGenerator(config);
        List<List<String>> matrix = generator.generate();
        CombinationChecker checker = new CombinationChecker(config);
        Output out = checker.check(bet, matrix);

        printOut(out);
    }

    private static void printOut(Output out) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(System.out, out);
    }
}
