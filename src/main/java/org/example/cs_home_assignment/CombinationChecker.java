package org.example.cs_home_assignment;

import org.example.cs_home_assignment.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.cs_home_assignment.model.SymbolImpact.*;
import static org.example.cs_home_assignment.model.SymbolType.bonus;
import static org.example.cs_home_assignment.model.SymbolType.standard;

public class CombinationChecker {
    private Map<String, Symbol> symbols;
    private Set<String> usefulBonusSymbols;

    private Map<String, WinCombinationChecker> winCheckers = new HashMap<>();
    public CombinationChecker(Config config) {
        symbols = config.symbols();

        config.win_combinations().forEach((name, combination) -> {
            WinCombinationChecker checker;
            WinCombination combinationWithName = new WinCombination(name, combination);
            if (WinCombinationWhen.same_symbols.equals(combination.when()))
                checker = new SameSymbolChecker(config, combinationWithName);
            else
                checker = new SameSymbolLinearChecker(config, combinationWithName);
            winCheckers.put(name, checker);
        });

        usefulBonusSymbols = config.symbols().entrySet().stream()
                .map(symbolEntry -> bonus.equals(symbolEntry.getValue().type())
                        && !miss.equals(symbolEntry.getValue().impact())
                        ? symbolEntry.getKey() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Output check(int bet, List<List<String>> field) {
        Map<String, List<WinCombination>> winStandardSymbols = checkForStandardSymbols(field);

        Map<String, Symbol> winBonusSymbols = checkForBonusSymbols(field);

        double reward = calculateReward(bet, winStandardSymbols, winBonusSymbols);

        Map<String, List<String>> winStandardSymbolsOut = winStandardSymbols.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream()
                        .map(WinCombination::name).collect(Collectors.toList())));

        List<String> appliedBonusSymbols = reward > 0 ? winBonusSymbols.keySet().stream().toList() : null;
        Output output = new Output(field, reward, winStandardSymbolsOut, appliedBonusSymbols);

        return output;
    }

    // we may want to use higher accuracy than double for real money
    public double calculateReward(int bet, Map<String, List<WinCombination>> winStandardSymbols, Map<String, Symbol> winBonusSymbols) {
        double multiplier = winStandardSymbols.entrySet().stream()
                .map(entry -> symbols.get(entry.getKey()).reward_multiplier() * entry.getValue().stream()
                        .map(WinCombination::reward_multiplier)
                        .reduce((a, b) -> a * b).orElse(0f))
                .reduce(Double::sum).orElse(0d);

        multiplier *= winBonusSymbols.values().stream()
                .filter(symbol -> multiply_reward.equals(symbol.impact()))
                .map(Symbol::reward_multiplier)
                .reduce((a, b) -> a * b).orElse(1d);

        double addition = winBonusSymbols.values().stream()
                .filter(symbol -> extra_bonus.equals(symbol.impact()))
                .map(Symbol::extra)
                .reduce((a, b) -> a + b).orElse(0);

        return multiplier > 0 ? bet * multiplier + addition : 0;
    }

    private Map<String, List<WinCombination>> checkForStandardSymbols(List<List<String>> field) {
        Map<String, List<WinCombination>> winStandardSymbols = new HashMap<>();

        winCheckers.forEach((name, checker) -> {
            Map<String, WinCombination> foundSymbols = checker.check(field);
            if (foundSymbols != null && !foundSymbols.isEmpty()) {
                foundSymbols.forEach((symbol, winCombination) -> {
                    if (winStandardSymbols.containsKey(symbol)) winStandardSymbols.get(symbol).add(winCombination);
                    else winStandardSymbols.put(symbol, new ArrayList<>(List.of(winCombination)));
                });
            }
        });

        return winStandardSymbols;
    }

    private Map<String, Symbol> checkForBonusSymbols(List<List<String>> field) {
        Map<String, Symbol> winBonusSymbols = new HashMap<>();

        field.forEach(row -> row.forEach(symbol -> {
            if (usefulBonusSymbols.contains(symbol)) winBonusSymbols.put(symbol, symbols.get(symbol));
        }));

        return winBonusSymbols;
    }
}
