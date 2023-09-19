package org.example.cs_home_assignment;

import org.example.cs_home_assignment.model.Config;

import java.util.*;

public class FieldGenerator {

    private int columns;
    private int rows;
    private List<List<Map<String, Integer>>> standardSymbolProbabilities;
    private Map<String, Integer> bonusSymbolProbabilities;

    public FieldGenerator(Config config) {
        columns = config.columns();
        rows = config.rows();

        standardSymbolProbabilities = new ArrayList<>(config.columns());

        for (int column = 0; column < columns; column++) {
            standardSymbolProbabilities.add(new ArrayList<>(rows));
            for (int row = 0; row < rows; row++) {
                standardSymbolProbabilities.get(column).add(null);
            }
        }

        config.probabilities().standard_symbols().forEach(probability -> {
            int column = probability.column();
            int row = probability.row();
            standardSymbolProbabilities.get(column).set(row, probability.symbols());
        });

        bonusSymbolProbabilities = config.probabilities().bonus_symbols().symbols();
    }

    public List<List<String>> generate() {
        List<List<String>> field = new ArrayList<>(columns);

        for (int column = 0; column < columns; column++) {
            field.add(new ArrayList<>());
            for (int row = 0; row < rows; row++) {
                field.get(column).add(getSymbol(standardSymbolProbabilities.get(column).get(row)));
            }
        }

        return field;
    }

    private String getSymbol(Map<String, Integer> standardSymbolProbabilityMap) {
        Map<Long, String> symbolDistribution = new HashMap<>();
        List<Double> distributionKeys = new LinkedList<>();
        long probabilitiesSum = 0;

        for (Map.Entry<String, Integer> entry : standardSymbolProbabilityMap.entrySet()) {
            String symbol = entry.getKey();
            Integer probability = entry.getValue();
            distributionKeys.add((double) probabilitiesSum);
            symbolDistribution.put(probabilitiesSum, symbol);
            probabilitiesSum += probability;
        }

        for (Map.Entry<String, Integer> entry : bonusSymbolProbabilities.entrySet()) {
            String symbol = entry.getKey();
            Integer probability = entry.getValue();
            distributionKeys.add((double) probabilitiesSum);
            symbolDistribution.put(probabilitiesSum, symbol);
            probabilitiesSum += probability;
        }

        double random =probabilitiesSum * Math.random();

        Double[] distributionKeysArray = distributionKeys.toArray(new Double[]{});
        Arrays.sort(distributionKeysArray);
        int keyPosition = Arrays.binarySearch(distributionKeysArray, random);
        if (keyPosition < 0 ) keyPosition = (-keyPosition) - 2;

        long distributionKey = Math.round(distributionKeysArray[keyPosition]);

        return symbolDistribution.get(distributionKey);
    }
}
