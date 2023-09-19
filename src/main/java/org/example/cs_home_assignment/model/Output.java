package org.example.cs_home_assignment.model;

import java.util.List;
import java.util.Map;

public record Output(
        List<List<String>> matrix,
        Double reward,
        Map<String, List<String>> applied_winning_combinations,
        List<String> applied_bonus_symbols
        ) {
}
