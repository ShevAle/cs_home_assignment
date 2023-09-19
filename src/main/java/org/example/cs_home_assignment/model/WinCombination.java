package org.example.cs_home_assignment.model;

import java.util.List;

public record WinCombination(
        String name,
        WinCombinationGroup group,
        Float reward_multiplier,
        WinCombinationWhen when,
        Integer count,
        List<List<String>> covered_areas) {
    public WinCombination(String name, WinCombination combination) {
        this(name, combination.group, combination.reward_multiplier, combination.when, combination.count, combination.covered_areas);
    }
}
