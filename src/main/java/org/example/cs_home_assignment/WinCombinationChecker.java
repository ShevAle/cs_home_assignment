package org.example.cs_home_assignment;

import org.example.cs_home_assignment.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.cs_home_assignment.model.SymbolType.standard;

public abstract class WinCombinationChecker {
    protected int columns;
    protected int rows;
    protected Map<String, Symbol> symbols;
    protected Set<String> standardSymbols;
    protected WinCombination winCombination;

    public WinCombinationChecker(Config config, WinCombination winCombination) {
        columns = config.columns();
        rows = config.rows();
        symbols = config.symbols();
        this.winCombination = winCombination;

        standardSymbols = config.symbols().entrySet().stream()
                .map(symbolEntry -> standard.equals(symbolEntry.getValue().type()) ? symbolEntry.getKey() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public abstract Map<String, WinCombination> check(List<List<String>> field);

    protected int column(String area) {
        return Integer.parseInt(area.substring(area.indexOf(":") + 1));
    }

    protected int row(String area) {
        return Integer.parseInt(area.substring(0, area.indexOf(":")));
    }
}
