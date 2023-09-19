package org.example.cs_home_assignment;

import org.example.cs_home_assignment.model.Config;
import org.example.cs_home_assignment.model.Symbol;
import org.example.cs_home_assignment.model.WinCombination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SameSymbolChecker extends WinCombinationChecker {

    public SameSymbolChecker(Config config, WinCombination winCombination) {
        super(config, winCombination);
    }

    @Override
    public Map<String, WinCombination> check(List<List<String>> field) {
        Map<String, Integer> foundSymbols = new HashMap<>();

        field.forEach(row -> row.forEach(symbol -> {
            if (standardSymbols.contains(symbol)) {
                foundSymbols.computeIfPresent(symbol, (s, c) -> c + 1);
                foundSymbols.putIfAbsent(symbol, 1);
            }
        }));

        Map<String, WinCombination> result = new HashMap<>();
        foundSymbols.forEach((symbol, count) -> {
            // here assume we have winning combination for every number of same symbol > minimal, so we will only get maximal
            if (count.equals(winCombination.count())) result.put(symbol, winCombination);
        });

        return result;
    }
}
