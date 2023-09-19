package org.example.cs_home_assignment.model;

import java.util.List;
import java.util.Map;

public record Config(
        Integer columns,
        Integer rows,
        Map<String, Symbol> symbols,
        Probabilities probabilities,
        Map<String, WinCombination> win_combinations
) {
}
