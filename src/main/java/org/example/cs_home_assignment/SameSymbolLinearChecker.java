package org.example.cs_home_assignment;

import org.example.cs_home_assignment.model.Config;
import org.example.cs_home_assignment.model.Symbol;
import org.example.cs_home_assignment.model.WinCombination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SameSymbolLinearChecker extends WinCombinationChecker {

    public SameSymbolLinearChecker(Config config, WinCombination winCombination) {
        super(config, winCombination);
    }

    @Override
    public Map<String, WinCombination> check(List<List<String>> field) {
        return winCombination.covered_areas().stream()
                .map(area -> {
                    String firstSymbol = null;
                    for (String cell : area) {
                        String symbol = field.get(row(cell)).get(column(cell));
                        if (!standardSymbols.contains(symbol)) return null;
                        if (firstSymbol == null) firstSymbol = symbol;
                        if (!symbol.equals(firstSymbol)) return null;
                    }
                    return firstSymbol;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(symbol -> symbol, symbol -> winCombination));
    }
}
